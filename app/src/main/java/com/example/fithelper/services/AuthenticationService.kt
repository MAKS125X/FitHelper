package com.example.fithelper.services

import android.content.Context
import com.example.fithelper.R
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

object AuthenticationService {
    private val auth by lazy { FirebaseAuth.getInstance() }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(context, options)
    }

    fun signInWithGoogleAccount(
        account: GoogleSignInAccount?,
    ): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        return auth.signInWithCredential(credential)
    }

    fun signOut(context: Context) {
        signOutFirebase()
        signOutGoogle(context)
    }

    fun userIsAuthorized() = auth.currentUser != null

    private fun signOutFirebase() {
        auth.signOut()
    }

    private fun signOutGoogle(context: Context) {
        getGoogleSignInClient(context).signOut()
    }
}