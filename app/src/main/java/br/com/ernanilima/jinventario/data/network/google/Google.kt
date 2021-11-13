package br.com.ernanilima.jinventario.data.network.google

import androidx.activity.result.ActivityResultLauncher
import android.content.Intent
import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.TipoResultado
import com.google.android.gms.common.api.ApiException

object Google {

    private var fragment: Fragment? = null
    private var startActivityForResult: ActivityResultLauncher<Intent>? = null

    private var _iResult: IResultadoFirebase? = null
    private val iResult get() = _iResult!!

    private var _iFirebaseAuth: IFirebaseAuth? = null
    private val iFirebaseAuth get() = _iFirebaseAuth!!

    /**
     * Realiza login com a conta do gmail
     * @param googleSignInClient GoogleSignInClient - cliente de login do google
     * @param iResult IResultadoFirebase - class para obter o resultado
     */
    fun loginGmailUser(googleSignInClient: GoogleSignInClient, iResult: IResultadoFirebase) {
        _iResult = iResult
        _iFirebaseAuth = FirebaseAuth(iResult)

        val intent = googleSignInClient.signInIntent
        startActivityForResult!!.launch(intent)
    }

    /**
     * Usado para construir novo startActivityForResult e onActivityResult
     * @param fragment Fragment - fragmento para exibir a intent
     */
    fun showActivityForResult(fragment: Fragment) {
        this.fragment = fragment
        buildRegisterForActivityResult()
    }

    /**
     * Constroi um callback para exibir e obter o resultado de login
     */
    private fun buildRegisterForActivityResult() {
        // https://developer.android.com/training/basics/intents/result
        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
        startActivityForResult = fragment!!.registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                setLoginResultPerformed(result.data!!)
            } else {
                // login cancelado
                iResult.setResultado(TipoResultado.UNAUTHENTICATED_USER)
            }
        }
    }

    /**
     * Converte os dados do usuario que realizou o logim
     * @param data Intent - dados do usuario
     */
    private fun setLoginResultPerformed(data: Intent) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val gmailUser = task.getResult(ApiException::class.java)
            iFirebaseAuth.loginGmailUser(gmailUser.idToken!!)
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }
}