package br.com.ernanilima.jinventario.ui.login

import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase

interface ILogin {
    /** [LoginViewModel]  */
    interface IViewModel : IResultadoFirebase {
        fun login()
    }
}