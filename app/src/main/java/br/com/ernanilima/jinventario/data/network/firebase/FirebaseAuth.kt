package br.com.ernanilima.jinventario.data.network.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase
import javax.inject.Inject
import br.com.ernanilima.jinventario.data.network.firebase.validation.ValidarFirebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseAuth @Inject constructor(): IFirebaseAuth {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var currentUser: FirebaseUser? = auth.currentUser

    private var _iResult: IResultadoFirebase? = null
    private val iResult get() = _iResult!!

    /**
     * @param iResult IResultadoFirebase - class para obter o resultado
     */
    constructor(iResult: IResultadoFirebase): this() {
        this._iResult = iResult
    }

    /**
     * Verifica se ja existe usuario conectado
     */
    override fun checkAuthenticatedUserToLogin() {
        if (currentUser != null && currentUser!!.isEmailVerified) {
            iResult.setResultado(TipoResultado.AUTHENTICATED_USER)
        } else {
            iResult.setResultado(TipoResultado.UNAUTHENTICATED_USER)
        }
    }

    /**
     * @return String - e-mail do usuario logado
     */
    override fun getUserEmail(): String {
        return currentUser?.email ?: ""
    }

    /**
     * Cadastra um usuario
     */
    override fun registerUser(context: Context, userEmail: String, userPassword: String) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                currentUser = auth.currentUser
                iResult.setResultado(TipoResultado.CADASTRO_REALIZADO)
            }
        }.addOnFailureListener { error ->
            ValidarFirebase.erroFirebase(context, (error as FirebaseAuthException).errorCode)
        }
    }

    /**
     * Envia e-mail de verificacao para o usuario
     */
    override fun sendEmailVerification(context: Context) {
        currentUser!!.sendEmailVerification().addOnCompleteListener { result ->
            if (result.isSuccessful) {
                iResult.setResultado(TipoResultado.EMAIL_VERIFICACAO_ENVIADO)
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
                iResult.setResultado(TipoResultado.EMAIL_NOVA_SENHA_ENVIADO)
            }
        }.addOnFailureListener { error ->
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
                currentUser = auth.currentUser
                iResult.setResultado(TipoResultado.LOGIN_REALIZADO)
            }
        }
    }

    /**
     * Realiza login do usuario que ja confirmou e-mail de verificacao
     */
    override fun loginUser(context: Context, userEmail: String, userPassword: String) {
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                currentUser = auth.currentUser
                val tipoResultado = if (result.result.user!!.isEmailVerified) TipoResultado.LOGIN_REALIZADO else TipoResultado.EMAIL_NAO_VERIFICADO
                iResult.setResultado(tipoResultado)
            }
        }.addOnFailureListener { error ->
            iResult.setResultado(TipoResultado.UNAUTHENTICATED_USER)
            ValidarFirebase.erroFirebase(context, (error as FirebaseAuthException).errorCode)
        }
    }
}