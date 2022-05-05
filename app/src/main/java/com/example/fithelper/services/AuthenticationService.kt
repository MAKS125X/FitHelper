package com.example.fithelper.services

import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

object AuthenticationService {
    private val authUI by lazy { AuthUI.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }

    private val providers by lazy {
        arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
    }

    fun createSignInIntentWithCurrentProvider(provider: AuthUI.IdpConfig): Intent =
        authUI.createSignInIntentBuilder()
            .setAvailableProviders(arrayListOf(provider))
            .build()


    fun signUpWithEmailAndPassword(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun signInWithEmailAndPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun userIsAuthorized(): Boolean = auth.currentUser != null
    fun signOut(context: Context) {
        authUI.signOut(context)
    }
}