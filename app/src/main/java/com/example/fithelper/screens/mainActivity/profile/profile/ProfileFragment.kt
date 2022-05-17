package com.example.fithelper.screens.mainActivity.profile.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.fithelper.screens.authenticationActivity.AuthenticationActivity
import com.example.fithelper.databinding.FragmentProfileBinding
import com.example.fithelper.services.AuthenticationService
import com.example.fithelper.services.UserService

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        prefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.helloTextView.text = "Привет, ${UserService.getUser()?.displayName ?: "безымянный пользователь"}"

        binding.maxWeightTextView.text = "Максимальное значение веса: ${prefs.getInt("APP_PREFERENCES_MAX_WEIGHT", 100)}"



//        binding.weightCountTextView.text =
//            "Значение веса по умолчанию при добавлении упражнения: ${prefs.getInt("APP_PREFERENCES_WEIGHT_COUNTER", 25)}"
//        binding.weightCountSeekBar.progress = prefs.getInt("APP_PREFERENCES_WEIGHT_COUNTER", 25)
//
//        binding.weightCountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                val editor = prefs.edit()
//                editor.putInt("APP_PREFERENCES_WEIGHT_COUNTER", p1).apply()
//                binding.weightCountTextView.text = "Значение веса по умолчанию при добавлении упражнения: $p1"
//            }
//            override fun onStartTrackingTouch(p0: SeekBar?) {}
//            override fun onStopTrackingTouch(p0: SeekBar?) {}
//        })

        binding.logoutButton.setOnClickListener {
            AuthenticationService.signOut(requireContext())
            val i = Intent(requireContext(), AuthenticationActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }
    }
}