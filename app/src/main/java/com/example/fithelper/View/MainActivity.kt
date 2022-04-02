package com.example.fithelper.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fithelper.R
import com.example.fithelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.selectedItemId = R.id.navigation_profile

            //   supportFragmentManager
            //.beginTransaction()
            //.replace(R.id.fragment_holder, ProfileFragment.newInstance())
            //.commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_holder, HomeFragment.newInstance())
                        .commit()
                }
                R.id.navigation_guide -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_holder, GuideFragment.newInstance())
                        .commit()
                }
                R.id.navigation_workout -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_holder, WorkoutFragment.newInstance())
                        .commit()
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