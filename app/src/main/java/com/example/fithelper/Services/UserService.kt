package com.example.fithelper.Services

import com.google.firebase.auth.FirebaseAuth

object UserService {
    private val auth = FirebaseAuth.getInstance()

    fun getUserId(): String {
        val user = auth.currentUser ?: return ""
        return user.uid;
    }
}
