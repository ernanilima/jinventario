package br.com.ernanilima.jinventario.extension.common.dialog

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.UiThread
import androidx.fragment.app.DialogFragment
import br.com.ernanilima.jinventario.databinding.DialogSimpleAlertBinding

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

    private var _binding: DialogSimpleAlertBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null
    private var message: String? = null
    private var titleNegativeButton: String? = null
    private var titlePositiveButton: String? = null
    private var negativeButtonBold: Int? = null
    private var positiveButtonBold: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogSimpleAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout(baseDialog)

        title?.let { binding.txtTitle.text = it }
        message?.let { binding.txtMessage.text = it }
        titleNegativeButton?.let { binding.btnNegative.text = it }
        titlePositiveButton?.let { binding.btnPositive.text = it }
        negativeButtonBold?.let { binding.btnNegative.setTypeface(null, it) }
        positiveButtonBold?.let { binding.btnPositive.setTypeface(null, it) }

        binding.btnNegative.tag = BtnAction.NEGATIVE
        binding.btnNegative.setOnClickListener(this)
        binding.btnPositive.tag = BtnAction.POSITIVE
        binding.btnPositive.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        this.isCancelable = false
        this.dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setTitle(title: String) {
        this.title = title
    }

    private fun setMessage(message: String) {
        this.message = message
    }

    private fun setNegativeButtonBold() {
        this.negativeButtonBold = Typeface.BOLD
    }

    private fun setPositiveButtonBold() {
        this.positiveButtonBold = Typeface.BOLD
    }

    private fun setTitleNegativeButton(titleNegativeButton: String) {
        this.titleNegativeButton = titleNegativeButton
    }

    private fun setTitlePositiveButton(titlePositiveButton: String) {
        this.titlePositiveButton = titlePositiveButton
    }

    @UiThread
    private fun setLayout(baseDialog: BaseDialog) {
        when (baseDialog.type) {
            Type.QUESTION -> {
                baseDialog.title?.let { setTitle(it) }
                baseDialog.message?.let { setMessage(it) }
                baseDialog.titleNegativeButton?.let { setTitleNegativeButton(it) }
                baseDialog.titlePositiveButton?.let { setTitlePositiveButton(it) }
                baseDialog.negativeButtonBold?.let { setNegativeButtonBold() }
                baseDialog.positiveButtonBold?.let { setPositiveButtonBold() }
            }
            // em breve tera a opcao de loading e alert
        }
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