package com.example.fithelper

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.fithelper.databinding.FragmentSettingsDialogBinding
import com.example.fithelper.services.UserService

class SettingsDialogFragment : DialogFragment() {

    lateinit var binding: FragmentSettingsDialogBinding
    lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)

        binding.maxWeightEditText.hint = prefs.getInt("APP_PREFERENCES_MAX_WEIGHT", 100).toString()

        binding.userNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                //TODO: Возможно отрицательное число
                UserService.updateProfile { builder ->
                    builder.displayName = p0.toString()
                    builder.build()
                }
            }
        })

        binding.maxWeightEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val intValue = p0.toString().toIntOrNull()

                if(intValue != null){
                    val editor = prefs.edit()
                    editor.putInt("APP_PREFERENCES_MAX_WEIGHT", p0.toString().toInt()).apply()
                }
            }
        })
    }
}