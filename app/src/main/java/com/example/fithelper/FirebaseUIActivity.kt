package com.example.fithelper

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fithelper.databinding.ActivityFirebaseBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.services.AuthenticationService
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

class FirebaseUIActivity : AppCompatActivity() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        if (res.resultCode == RESULT_OK) {
            startMainActivity()
        } else {
            Toast.makeText(
                this,
                res.idpResponse?.error?.message.toString(), // "Please try again"
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private lateinit var binding: ActivityFirebaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AuthenticationService.userIsAuthorized())
            startMainActivity()

        super.onCreate(savedInstanceState)

        binding = ActivityFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInWithGoogleBTN.setOnClickListener {
            val intent = AuthenticationService.createSignInIntentWithCurrentProvider(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            signInLauncher.launch(intent)
        }

        // todo: должна быть регистрация по почте во фрагменте, тестовая штука
        binding.signInWithTV.setOnClickListener {
            val intent = AuthenticationService.createSignInIntentWithCurrentProvider(
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            signInLauncher.launch(intent)
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}