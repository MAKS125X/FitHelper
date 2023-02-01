package com.example.fithelper.screens.authenticationActivity.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fithelper.R
import com.example.fithelper.databinding.FragmentForgotPasswordBinding
import com.example.fithelper.services.AuthenticationService

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val args by navArgs<ForgotPasswordFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailForResetPasswordET.setText(args.email)

        binding.resetPasswordBTN.setOnClickListener {
            val email = binding.emailForResetPasswordET.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.enter_email), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AuthenticationService.resetPassword(email)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), getString(R.string.check_email_for, getString(R.string.change_password)), Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { ex ->
                    Toast.makeText(requireContext(), ex.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}