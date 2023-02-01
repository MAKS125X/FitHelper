package com.example.fithelper.screens.mainActivity.profile.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fithelper.R
import com.example.fithelper.screens.authenticationActivity.AuthenticationActivity
import com.example.fithelper.databinding.FragmentProfileBinding
import com.example.fithelper.services.AuthenticationService

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val settingsViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsViewModel.userName.observe(viewLifecycleOwner){ userName ->
            setHelloTextView(userName)
        }

        binding.logoutButton.setOnClickListener {
            AuthenticationService.signOut(requireContext())
            val i = Intent(requireContext(), AuthenticationActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }
    }

    fun setHelloTextView(userName: String?){
        if(userName.isNullOrEmpty()){
            binding.helloTextView.text = resources.getString(R.string.hello_empty_user)
        }
        else{
            binding.helloTextView.text = resources.getString(R.string.hello_user, userName)
        }
    }
}