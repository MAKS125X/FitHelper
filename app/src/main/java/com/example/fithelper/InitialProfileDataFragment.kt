package com.example.fithelper

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fithelper.databinding.FragmentInitialProfileDataBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.services.UserService

class InitialProfileDataFragment : Fragment() {

    lateinit var binding: FragmentInitialProfileDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitialProfileDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmProfileDataButton.setOnClickListener{

            val userName = binding.nameEditText.text.toString()
            if(userName.isEmpty()){
                Toast.makeText(requireContext(), resources.getString(R.string.empty_username), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            UserService.updateProfile { builder ->
                builder.displayName = userName
                builder.build()
            }

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.skipTextView.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}