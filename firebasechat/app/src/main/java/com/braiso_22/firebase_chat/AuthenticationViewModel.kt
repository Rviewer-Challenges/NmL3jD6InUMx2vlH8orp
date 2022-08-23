package com.braiso_22.firebase_chat

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
val authViewModel = AuthenticationViewModel()

class AuthenticationViewModel {

    fun registerWithEmailAndPass(
        email: String,
        password: String,
        navigate: (successful: Boolean) -> Unit
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {
            navigate(it.isSuccessful)
        }
    }


    fun loginWithEmailAndPass(
        email: String,
        password: String,
        navigate: (successful: Boolean) -> Unit
    ) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {
            navigate(it.isSuccessful)
        }
    }

    fun loginWithGoogle(context: Context): Intent {
        val googleOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.webclient_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(context, googleOptions)
        googleClient.signOut()
        FirebaseAuth.getInstance().signOut()
        return googleClient.signInIntent
    }

    fun signInWithGoogle(signInIntent: Intent?, navigate: (successful: Boolean) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(signInIntent)
        try {
            val account = task.getResult(ApiException::class.java)
            val token = account.idToken
            val credential = GoogleAuthProvider.getCredential(token, null)
            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                navigate(it.isSuccessful)
            }
        } catch (e: ApiException) {
            throw e
        }
    }
}