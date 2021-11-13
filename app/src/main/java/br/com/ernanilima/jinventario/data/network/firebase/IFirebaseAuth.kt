package br.com.ernanilima.jinventario.data.network.firebase

import android.content.Context

/**
 * Metodos da classe [FirebaseAuth]
 */
interface IFirebaseAuth {
    fun checkAuthenticatedUserToLogin()
    fun getUserEmail(): String
    fun registerUser(context: Context, userEmail: String, userPassword: String)
    fun sendEmailVerification(context: Context)
    fun sendEmailForgotPassword(context: Context, userEmail: String)
    fun loginGmailUser(userToken: String)
    fun loginUser(context: Context, userEmail: String, userPassword: String)
}