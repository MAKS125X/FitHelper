package com.example.fithelper.screens.authActivity.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.fithelper.R
import com.example.fithelper.databinding.FragmentLoginBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.screens.mainActivity.profile.profile.ProfileFragment
import com.example.fithelper.services.UserService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        firebaseAuthWithGoogle(account.idToken!!)
                    }
                } catch (e: ApiException) {
                    Toast.makeText(requireContext(), "Ошибка при входе", Toast.LENGTH_SHORT).show()
                }
            }
        binding.accountButton.setOnClickListener {
            signInWithGoogle()
        }

    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signInWithGoogle() {
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        UserService.signInWithGoogle(idToken)
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> checkAuthState()
                    task.isCanceled -> Toast.makeText(requireContext(), "Аутентификация отменена", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(requireContext(), task.exception?.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkAuthState() {
        if (UserService.userIsAuthorized()) {
            val i = Intent(requireContext(), MainActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        } else {
            Toast.makeText(requireContext(), "Пожалуйста, авторизируйтесь", Toast.LENGTH_SHORT).show()
        }
    }
}