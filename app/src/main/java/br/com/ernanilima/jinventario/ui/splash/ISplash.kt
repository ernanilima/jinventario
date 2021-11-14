package br.com.ernanilima.jinventario.ui.splash

import br.com.ernanilima.jinventario.data.result.IResult

interface ISplash {
    /** [SplashViewModel] */
    interface IViewModel : IResult {
        /**
         * Verifica se existe usuario autenticado
         */
        fun checkAuthenticatedUserToLogin()
    }
}