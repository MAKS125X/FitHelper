package com.example.fithelper.Features

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fithelper.R
import com.example.fithelper.Services.UserService
import com.example.fithelper.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    // todo: перенести логику в UserService
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutButton.setOnClickListener {
            UserService.singOut()
            val i = Intent(context, LoginActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}