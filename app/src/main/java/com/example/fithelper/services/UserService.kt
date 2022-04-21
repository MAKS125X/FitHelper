package com.example.fithelper.services

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

object UserService {
    private val auth = FirebaseAuth.getInstance()

    fun signInWithGoogle(token: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(token, null)
        return auth.signInWithCredential(credential)
    }

    fun singOut() =
        auth.signOut()

    fun getUserId(): String =
        auth.currentUser?.uid ?: "";

    fun userIsAuthorized(): Boolean =
        auth.currentUser != null
}
