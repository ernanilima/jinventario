package br.com.ernanilima.jinventario.ui.login

import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase
import com.google.android.gms.auth.api.signin.GoogleSignInClient

interface ILogin {
    /** [LoginViewModel]  */
    interface IViewModel : IResultadoFirebase {
        /**
         * Realiza login
         * @param userEmail String - e-mail do usuario
         * @param userPassword String - senha do usuario
         */
        fun login(userEmail: String, userPassword: String)

        /**
         * * Realiza login com o gmail
         * @param loginGmail GoogleSignInClient - classe do google
         */
        fun loginGmail(loginGmail: GoogleSignInClient)

        /**
         * Envia email de verificacao
         */
        fun submitVerification()
    }
}