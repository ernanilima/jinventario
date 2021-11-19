package br.com.ernanilima.jinventario.ui.register

import br.com.ernanilima.jinventario.data.result.IResult

interface IRegister {
    /** [RegisterViewModel] */
    interface IViewModel : IResult {
        fun register()
    }
}