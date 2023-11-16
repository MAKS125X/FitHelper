package com.example.fithelper.screens.mainActivity.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.fithelper.screens.mainActivity.profile.ProfileViewModel
import com.example.fithelper.databinding.DialogSettingsBinding


class SettingsDialogFragment : DialogFragment() {

    lateinit var binding: DialogSettingsBinding
    lateinit var prefs: SharedPreferences

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        binding.userNameTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                profileViewModel.setUserName(p0.toString())
            }
        })

        binding.maxWeightTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val intValue = p0.toString().toIntOrNull()

                if(intValue != null){
                    val editor = prefs.edit()
                    editor.putInt(PREFS_TAG, p0.toString().toInt()).apply()
                }
            }
        })
    }

    companion object{
        const val PREFS_NAME = "settings"
        const val PREFS_TAG = "APP_PREFERENCES_MAX_WEIGHT"
    }
}