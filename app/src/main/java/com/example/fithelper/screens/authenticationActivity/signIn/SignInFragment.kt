package com.example.fithelper.screens.authenticationActivity.signIn

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fithelper.databinding.FragmentSignInBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.services.AuthenticationService

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInConfirmBTN.setOnClickListener {
            val email = binding.emailSignUpET.text.toString()
            val password = binding.passwordSignUpET.text.toString()

            AuthenticationService.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }

        binding.signUpBTN.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }
}