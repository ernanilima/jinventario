package br.com.ernanilima.jinventario.ui.login

import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase
import com.google.android.gms.auth.api.signin.GoogleSignInClient

interface ILogin {
    /** [LoginViewModel]  */
    interface IViewModel : IResultadoFirebase {
        fun login(userEmail: String, userPassword: String)
        fun loginGmail(loginGoogle: GoogleSignInClient)
        fun checkAuthenticatedUserToLogin()
    }
}