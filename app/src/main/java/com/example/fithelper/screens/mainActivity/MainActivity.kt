package com.example.fithelper.screens.mainActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fithelper.R
import com.example.fithelper.databinding.ActivityMainBinding
import com.example.fithelper.screens.authActivity.AuthActivity
import com.example.fithelper.services.AuthenticationService
import com.example.fithelper.services.UserService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!AuthenticationService.userIsAuthorized()) {
            val intent = Intent(this, AuthActivity::class.java)
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