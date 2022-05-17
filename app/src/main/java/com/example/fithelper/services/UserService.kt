package com.example.fithelper.services

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

object UserService {
    private val auth by lazy { FirebaseAuth.getInstance() }

    fun getUserId(): String? = auth.currentUser?.uid

    fun updateEmail(email: String) =
        auth.currentUser?.updateEmail(email)

    fun updatePassword(password: String) =
        auth.currentUser?.updatePassword(password)

    fun getUser() = auth.currentUser

    fun updateProfile(requestBuilder: ((UserProfileChangeRequest.Builder) -> UserProfileChangeRequest)): Task<Void>? {
        val request = requestBuilder(UserProfileChangeRequest.Builder())
        return auth.currentUser?.updateProfile(request)
    }

    fun deleteUser() =
        auth.currentUser?.delete()
}
