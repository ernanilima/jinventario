package br.com.ernanilima.jinventario.data.network.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import br.com.ernanilima.jinventario.data.result.IResult
import javax.inject.Inject
import br.com.ernanilima.jinventario.data.network.firebase.validation.ValidarFirebase
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseAuth @Inject constructor(): IFirebaseAuth {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _iResult: IResult? = null
    private val iResult get() = _iResult!!

    /**
     * @param iResult IResultadoFirebase - class para obter o resultado
     */
    constructor(iResult: IResult): this() {
        this._iResult = iResult
    }

    /**
     * Verifica se ja existe usuario conectado
     */
    override fun checkAuthenticatedUserToLogin() {
        if (auth.currentUser != null && auth.currentUser!!.isEmailVerified) {
            iResult.setResult(ResultTypeFirebase.AUTHENTICATED_USER)
        } else {
            iResult.setResult(ResultTypeFirebase.UNAUTHENTICATED_USER)
        }
    }

    /**
     * @return String - e-mail do usuario logado
     */
    override fun getUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }

    /**
     * Cadastra um usuario
     */
    override fun registerUser(context: Context, userEmail: String, userPassword: String) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                iResult.setResult(ResultTypeFirebase.REGISTRATION_DONE)
            }
        }.addOnFailureListener { error ->
            iResult.setResult(ResultTypeFirebase.REGISTRATION_NOT_DONE)
            ValidarFirebase.erroFirebase(context, (error as FirebaseAuthException).errorCode)
        }
    }

    /**
     * Envia e-mail de verificacao para o usuario
     */
    override fun sendEmailVerification(context: Context) {
        auth.currentUser!!.sendEmailVerification().addOnCompleteListener { result ->
            if (result.isSuccessful) {
                iResult.setResult(ResultTypeFirebase.VERIFICATION_EMAIL_SENT)
            }
        }.addOnFailureListener { error ->
            ValidarFirebase.erroFirebase(context, error.javaClass.simpleName)
        }
    }

    /**
     * Envia e-mail para o usuario redefinir a senha
     */
    override fun sendEmailForgotPassword(context: Context, userEmail: String) {
        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                iResult.setResult(ResultTypeFirebase.NEW_PASSWORD_EMAIL_SENT)
            }
        }.addOnFailureListener { error ->
            iResult.setResult(ResultTypeFirebase.NEW_PASSWORD_EMAIL_NOT_SENT)
            ValidarFirebase.erroFirebase(context, (error as FirebaseAuthException).errorCode)
        }
    }

    /**
     * Realiza login com a conta do gmail
     */
    override fun loginGmailUser(userToken: String) {
        val credential = GoogleAuthProvider.getCredential(userToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                iResult.setResult(ResultTypeFirebase.LOGIN_DONE)
            }
        }
    }

    /**
     * Realiza login do usuario que ja confirmou e-mail de verificacao
     */
    override fun loginUser(context: Context, userEmail: String, userPassword: String) {
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                val resultTypeFirebase = if (result.result.user!!.isEmailVerified) ResultTypeFirebase.LOGIN_DONE else ResultTypeFirebase.EMAIL_NOT_VERIFIED
                iResult.setResult(resultTypeFirebase)
            }
        }.addOnFailureListener { error ->
            // criar validacao para a classe FirebaseTooManyRequestsException
            iResult.setResult(ResultTypeFirebase.UNAUTHENTICATED_USER)
            ValidarFirebase.erroFirebase(context, (error as FirebaseAuthException).errorCode)
        }
    }
}