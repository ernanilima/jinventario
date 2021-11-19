package br.com.ernanilima.jinventario.ui.login

import br.com.ernanilima.jinventario.data.result.IResult

interface ILogin {
    /** [LoginViewModel] */
    interface IViewModel : IResult {
        /**
         * Realiza login
         */
        fun login()

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