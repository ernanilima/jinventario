package br.com.ernanilima.jinventario.ui.forgotPassword

import br.com.ernanilima.jinventario.data.result.IResult

interface IForgotPassword {
    /** [ForgotPasswordViewModel] */
    interface IViewModel : IResult {
        fun sendNewPassword()
    }
}