package br.com.ernanilima.jinventario.data.network.firebase

/**
 * Metodos da classe [FirebaseAuth]
 */
interface IFirebaseAuth {
    fun checkAuthenticatedUserToLogin()
    fun getUserEmail(): String
    fun registerUser(userEmail: String, userPassword: String)
    fun sendEmailVerification()
    fun sendEmailForgotPassword(userEmail: String)
    fun loginGmailUser(userToken: String)
    fun loginUser(userEmail: String, userPassword: String)
}