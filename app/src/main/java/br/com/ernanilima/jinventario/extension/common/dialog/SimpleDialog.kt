package br.com.ernanilima.jinventario.extension.common.dialog

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.UiThread
import androidx.fragment.app.DialogFragment
import br.com.ernanilima.jinventario.databinding.DialogSimpleQuestionBinding

private enum class BtnAction { POSITIVE, NEGATIVE }
enum class Type { QUESTION, BASE }
typealias BtnNegativeCallback = () -> Unit
typealias BtnPositiveCallback = () -> Unit

/**
 * Referencia [link](https://github.com/BROUDING/SimpleDialog)
 */
class SimpleDialog constructor(
    private val baseDialog: BaseDialog
) : DialogFragment(), View.OnClickListener {

    private var _bindingQuestion: DialogSimpleQuestionBinding? = null
    private val bindingQuestion get() = _bindingQuestion!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return when (baseDialog.type) {
            Type.QUESTION -> {
                _bindingQuestion = DialogSimpleQuestionBinding.inflate(inflater, container, false)
                setLayoutQuestion(baseDialog)
                bindingQuestion.root
            }
            else -> {
                super.onCreateView(inflater, container, savedInstanceState)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        this.isCancelable = false
        this.dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    @UiThread
    private fun setLayoutQuestion(baseDialog: BaseDialog) {
        baseDialog.also {
            it.title?.let { t -> bindingQuestion.txtTitle.text = t }
            it.message?.let { m -> bindingQuestion.txtMessage.text = m }
            it.titleNegativeButton?.let { tnb -> bindingQuestion.btnNegative.text = tnb }
            it.titlePositiveButton?.let { tpb -> bindingQuestion.btnPositive.text = tpb }
            it.negativeButtonBold?.let { bindingQuestion.btnNegative.setTypeface(null, Typeface.BOLD) }
            it.positiveButtonBold?.let { bindingQuestion.btnPositive.setTypeface(null, Typeface.BOLD) }
        }

        bindingQuestion.btnNegative.tag = BtnAction.NEGATIVE
        bindingQuestion.btnNegative.setOnClickListener(this)
        bindingQuestion.btnPositive.tag = BtnAction.POSITIVE
        bindingQuestion.btnPositive.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.tag as BtnAction) {
            BtnAction.NEGATIVE -> {
                baseDialog.btnNegativeCallback?.invoke()
            }
            BtnAction.POSITIVE -> {
                baseDialog.btnPositiveCallback?.invoke()
            }
        }
        dismiss()
    }

    fun show() {
        this.show(baseDialog.fragmentManager, this::class.simpleName)
    }
}