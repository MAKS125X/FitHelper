package com.example.fithelper.screens.authenticationActivity.signUp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fithelper.databinding.FragmentSignUpBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.services.AuthenticationService

class SignUpFragment : Fragment() {
    private lateinit var  binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmSignUpButton.setOnClickListener {
            val email = binding.emailSignUpET.text.toString()
            val password = binding.passwordSignUpET.text.toString()

            AuthenticationService.signUpWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val action = SignUpFragmentDirections.actionSignUpFragmentToInitialProfileData()
                    findNavController().navigate(action)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }

//
//        binding.signInBTN.setOnClickListener {
//            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
//            findNavController().navigate(action)
//        }
    }
}