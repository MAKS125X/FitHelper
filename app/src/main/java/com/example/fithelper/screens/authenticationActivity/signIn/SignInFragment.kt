package com.example.fithelper.screens.authenticationActivity.signIn

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.fithelper.R
import com.example.fithelper.databinding.FragmentSignInBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.services.AuthenticationService
import com.example.fithelper.services.Providers
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        if (res.resultCode == AppCompatActivity.RESULT_OK) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.try_again),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInConfirmBTN.setOnClickListener {
            val email = binding.emailSignInET.text.toString()
            val password = binding.passwordSignInET.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.fill_all_details_to_sign_in),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            AuthenticationService.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
        }

        binding.registerTextView.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.googleImageView.setOnClickListener {
            val intent =
                AuthenticationService.createSignInIntentWithCurrentProvider(Providers.Google)

            signInLauncher.launch(intent)
        }

        binding.forgetPasswordTV.setOnClickListener {
            val email = binding.emailSignInET.text.toString()
            val action = SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment()
            action.email = email

            findNavController().navigate(action)
        }
    }
}