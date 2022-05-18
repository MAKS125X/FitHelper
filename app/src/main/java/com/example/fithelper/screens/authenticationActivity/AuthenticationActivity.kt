package com.example.fithelper.screens.authenticationActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fithelper.databinding.ActivityFirebaseBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.services.AuthenticationService

class AuthenticationActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFirebaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AuthenticationService.userIsAuthorized())
            startMainActivity()

        super.onCreate(savedInstanceState)

        binding = ActivityFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}