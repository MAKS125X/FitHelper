package com.example.fithelper.screens.mainActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fithelper.R
import com.example.fithelper.databinding.ActivityMainBinding
import com.example.fithelper.screens.authenticationActivity.AuthenticationActivity
import com.example.fithelper.services.AuthenticationService
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!AuthenticationService.userIsAuthorized()) {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.main_fragment_holder)

        binding.bottomNavigationView.setupWithNavController(navController)

    }
}