package com.example.fithelper.screens.mainActivity.profile.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.services.UserService

class ProfileViewModel : ViewModel(){

    val userName = MutableLiveData<String>()

    init {
        userName.value = UserService.getUser()?.displayName
    }

    fun setUserName(string: String){
        userName.value = string
        UserService.updateProfile { builder ->
            builder.displayName = string
            builder.build()
        }
    }
}