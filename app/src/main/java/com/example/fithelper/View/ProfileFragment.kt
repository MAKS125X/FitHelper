package com.example.fithelper.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.fithelper.Repository.LoginViewModel
import com.example.fithelper.databinding.FragmentProfileBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    binding.textView2.text = "Привет, ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                    binding.logoutButton.setOnClickListener {
                        AuthUI.getInstance().signOut(requireContext())
                    }
                }
                else -> {
                    binding.logoutButton.setOnClickListener {
                        Toast.makeText(requireContext(), "Вы не можете выйти", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        binding.logoutButton.setOnClickListener {
            if(viewModel.authenticationState.value
                == LoginViewModel.AuthenticationState.AUTHENTICATED){
                val auth = Firebase.auth
                auth.signOut()
                requireActivity().finish()
            }
            else{
                Toast.makeText(requireContext(), "Входа в аккаунт не произведено, \nВы не можете выйти", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}