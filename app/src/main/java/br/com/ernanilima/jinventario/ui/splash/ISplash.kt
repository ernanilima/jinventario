package br.com.ernanilima.jinventario.ui.splash

import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase

interface ISplash {
    /** [SplashViewModel] */
    interface IViewModel : IResultadoFirebase {
        /**
         * Verifica se existe usuario autenticado
         */
        fun checkAuthenticatedUserToLogin()
    }
}