package com.example.fithelper.screens.authenticationActivity.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.fithelper.R
import com.example.fithelper.databinding.FragmentSignUpBinding
import com.example.fithelper.services.AuthenticationService

class SignUpFragment : Fragment() {
    private lateinit var  binding: FragmentSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            val email = binding.emailSignUpET.text.toString()
            val password = binding.passwordSignUpET.text.toString()
            val repeatPassword = binding.confirmPasswordSignUpET.text.toString()

            if(email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()){
                Toast.makeText(requireContext(), resources.getString(R.string.fill_all_registration_details), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password != repeatPassword){
                Toast.makeText(requireContext(), resources.getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AuthenticationService.signUpWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val action = SignUpFragmentDirections.actionSignUpFragmentToInitialProfileData()
                    findNavController().navigate(action)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}