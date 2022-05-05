package com.example.fithelper

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.services.AuthenticationService
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

class FirebaseUIActivity : AppCompatActivity() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        if (res.resultCode == RESULT_OK) {
            startMainActivity()
        }
        else {
            Toast.makeText(this, res.idpResponse?.error?.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AuthenticationService.userIsAuthorized())
            startMainActivity()
        else {
            val signInIntent = AuthenticationService.createSignInIntent()
            signInLauncher.launch(signInIntent)
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}