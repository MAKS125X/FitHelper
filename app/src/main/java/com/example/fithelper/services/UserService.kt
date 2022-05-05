package com.example.fithelper.services

import com.google.firebase.auth.FirebaseAuth

object UserService {
    private val auth by lazy { FirebaseAuth.getInstance() }

    fun getUserId(): String? = auth.currentUser?.uid
}
