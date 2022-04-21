package com.example.fithelper.Features

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.fithelper.R
import com.example.fithelper.Services.UserService
import com.example.fithelper.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {
    // todo: перенести логику в UserService
    lateinit var binding: ActivityLoginBinding
    lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        firebaseAuthWithGoogle(account.idToken!!)
                    }
                } catch (e: ApiException) {
                    Toast.makeText(this, "Ошибка при входе", Toast.LENGTH_SHORT).show()
                }
            }
        binding.accountButton.setOnClickListener {
            signInWithGoogle()
        }

        checkAuthState()
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    private fun signInWithGoogle() {
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        UserService.signInWithGoogle(idToken)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Успешный вход", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Произошла ошибка при входе", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkAuthState() {
        if (UserService.userIsAuthorized()) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}