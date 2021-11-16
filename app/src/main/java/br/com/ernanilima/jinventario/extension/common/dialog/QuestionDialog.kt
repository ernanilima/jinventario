package br.com.ernanilima.jinventario.extension.common.dialog

import androidx.fragment.app.FragmentManager

class QuestionDialog constructor(supportFragmentManager: FragmentManager): BaseDialog(supportFragmentManager) {

    init {
        type = Type.QUESTION
    }

    fun setTitle(title: String): QuestionDialog {
        super.title = title
        return this
    }

    fun setMessage(message: String): QuestionDialog {
        super.message = message
        return this
    }

    fun setTitleNegativeButton(titleNegativeButton: String): QuestionDialog {
        super.titleNegativeButton = titleNegativeButton
        return this
    }

    fun setTitlePositiveButton(titlePositiveButton: String): QuestionDialog {
        super.titlePositiveButton = titlePositiveButton
        return this
    }

    fun setNegativeButtonBold(): QuestionDialog {
        super.negativeButtonBold = true
        return this
    }

    fun setPositiveButtonBold(): QuestionDialog {
        super.positiveButtonBold = true
        return this
    }

    fun setNegativeButton(titleNegativeButton: String? = null, btnNegativeCallback: BtnNegativeCallback): QuestionDialog {
        titleNegativeButton?.let { super.titleNegativeButton = it }
        super.btnNegativeCallback = btnNegativeCallback
        return this
    }

    fun setPositiveButton(titlePositiveButton: String? = null, btnPositiveCallback: BtnPositiveCallback): QuestionDialog {
        titlePositiveButton?.let { super.titlePositiveButton = it }
        super.btnPositiveCallback = btnPositiveCallback
        return this
    }
}