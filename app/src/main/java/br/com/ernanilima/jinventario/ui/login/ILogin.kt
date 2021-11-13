package br.com.ernanilima.jinventario.ui.login

import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase

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
         */
        fun loginGmail()

        /**
         * Envia email de verificacao
         */
        fun sendEmailVerification()
    }
}