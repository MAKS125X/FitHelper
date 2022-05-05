package com.example.fithelper.screens.authActivity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.fithelper.databinding.FragmentLoginBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.services.AuthenticationService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //googleSignInClient = AuthenticationService.getGoogleSignInClient(requireActivity())
    }
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
                val accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
          //      try {
          //          val account = accountTask.getResult(ApiException::class.java)
          //          AuthenticationService.signInWithGoogleAccount(account)
          //              .addOnSuccessListener { authResult ->
          //                  val message =
          //                      if (authResult.additionalUserInfo!!.isNewUser) "Account created" else "SignIn Success"
//
          //                  Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//
          //                  val intent = Intent(context, MainActivity::class.java)
          //                  startActivity(intent)
          //                  requireActivity().finish()
          //              }
          //              .addOnFailureListener { e ->
          //                  Log.e("AUTH", e.message.toString())
          //              }
          //      } catch (e: ApiException) {
          //          Toast.makeText(requireContext(), e.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
          //      }
            }

        binding.accountButton.setOnClickListener {
            launcher.launch(googleSignInClient.signInIntent)
        }
    }
}