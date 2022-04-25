package com.example.fithelper.screens.mainActivity.profile.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fithelper.services.UserService
import com.example.fithelper.databinding.FragmentProfileBinding
import com.example.fithelper.screens.authActivity.AuthActivity

class ProfileFragment : Fragment() {
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
            val i = Intent(context, AuthActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }
    }
}