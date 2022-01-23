package com.example.fithelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fithelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_holder, ProfileFragment.newInstance())
            .commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {

                }
                R.id.navigation_guide -> {

                }
                R.id.navigation_workout -> {

                }
                R.id.navigation_profile -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_holder, ProfileFragment.newInstance())
                        .commit()
                }
            }
            true
        }
    }

}