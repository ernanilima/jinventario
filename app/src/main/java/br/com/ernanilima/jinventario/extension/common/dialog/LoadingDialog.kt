package br.com.ernanilima.jinventario.extension.common.dialog

import androidx.fragment.app.FragmentManager

class LoadingDialog constructor(supportFragmentManager: FragmentManager): BaseDialog(supportFragmentManager) {

    init {
        type = Type.LOADING
    }

    fun setMessage(message: String): LoadingDialog {
        super.message = message
        return this
    }
}