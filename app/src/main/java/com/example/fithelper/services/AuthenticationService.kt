package com.example.fithelper.services

import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings

object AuthenticationService {
    private val authUI by lazy { AuthUI.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }

    private val providers by lazy {
        mapOf(
            Providers.Email to AuthUI.IdpConfig.EmailBuilder().build(),
            Providers.Google to AuthUI.IdpConfig.GoogleBuilder().build()
        )
    }

    fun createSignInIntentWithCurrentProvider(provider: Enum<Providers>): Intent =
        authUI.createSignInIntentBuilder()
            .setAvailableProviders(arrayListOf(providers[provider]))
            .build()

    fun signUpWithEmailAndPassword(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun signInWithEmailAndPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun sendEmailVerification() =
        auth.currentUser?.sendEmailVerification() ?: Unit

    fun resetPassword(email: String) =
        auth.sendPasswordResetEmail(email)

    fun userIsAuthorized(): Boolean = auth.currentUser != null
    fun signOut(context: Context) {
        authUI.signOut(context)
    }
}

enum class Providers {
    Email, Google
}