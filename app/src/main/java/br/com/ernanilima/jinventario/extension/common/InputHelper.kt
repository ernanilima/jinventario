package br.com.ernanilima.jinventario.extension.common

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import br.com.ernanilima.jinventario.R
import com.google.android.material.textfield.TextInputLayout

/**
 * Realiza validacoes em tempo de execucao:
 * Campo obrigatorio.
 * Tamanho minimo do campo.
 * Campo de e-mail que combine com e-mail.
 * Senhas que combinem.
 */
class InputHelper constructor(private val activity: Activity) {

    private lateinit var validationWatcher: ValidationWatcher

    private var inputLayout: TextInputLayout? = null
    private var inputLayoutForMatch: TextInputLayout? = null
    private var required = false
    private var min: Int = Int.MIN_VALUE
    private var minLength: Int = 0

    companion object {
        const val ERROR_EMPTY = 0
        const val ERROR_MIN = 1
        const val ERROR_LENGTH = 2
        const val ERROR_EMAIL = 3
        const val PASSWORD_NOT_MATCH = 4
    }

    fun setInputLayout(inputLayout: TextInputLayout): InputHelper {
        this.inputLayout = inputLayout
        return this
    }

    fun setInputLayoutForMatch(inputLayoutForMatch: TextInputLayout): InputHelper {
        this.inputLayoutForMatch = inputLayoutForMatch
        return this
    }

    fun setRequired(required: Boolean): InputHelper {
        this.required = required
        return this
    }

    fun setMin(min: Int): InputHelper {
        this.min = min
        return this
    }

    fun setMinLength(minLength: Int): InputHelper {
        this.minLength = minLength
        return this
    }

    fun build() {
        validationWatcher = ValidationWatcher(activity, inputLayout, inputLayoutForMatch, required, min, minLength)
        inputLayout?.editText?.addTextChangedListener(validationWatcher)
    }

    private class ValidationWatcher constructor(
        private val activity: Activity,
        private val inputLayout: TextInputLayout?,
        private val inputLayoutForMatch: TextInputLayout?,
        private val required: Boolean,
        private val min: Int,
        private val minLength: Int
    ) : TextWatcher {

        private lateinit var value: String

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        override fun afterTextChanged(s: Editable?) {
            if (isNull()) { return }

            value = s.toString()

            (validationForEmptyValue() && validationMin() && validationMinLength() &&
                    validationForMatch() && validationForType(inputLayout!!.editText!!.inputType))
        }

        /**
         * Verifica se o conteudo recebido eh nulo
         * @return Boolean - true se for nulo
         */
        private fun isNull(): Boolean {
            return inputLayout == null
        }

        /**
         * Realiza a validacao de valor vazio
         * @return Boolean - false se tiver erro
         */
        private fun validationForEmptyValue(): Boolean {
            return if (value.isEmpty() && required) { setError(ERROR_EMPTY) }
            else { removeError() }
        }

        /**
         * Realiza a validacao do valor minimo aceito
         * @return Boolean - false se tiver erro
         */
        private fun validationMin(): Boolean {
            val valueInt = value.replace(Regex("[^0-9]"), "").toIntOrNull()
            return if (valueInt != null && valueInt < min) { setError(ERROR_MIN) }
            else { removeError() }
        }

        /**
         * Realiza a validacao de tamanho do dado
         * @return Boolean - false se tiver erro
         */
        private fun validationMinLength(): Boolean {
            return if (value.length < minLength) { setError(ERROR_LENGTH) }
            else { removeError() }
        }

        /**
         * Realiza a validacao de combinacoes de valores
         * @return Boolean - false se tiver erro
         */
        private fun validationForMatch(): Boolean {
            return if (inputLayoutForMatch != null &&
                inputLayout!!.editText!!.text.toString() != inputLayoutForMatch.editText!!.text.toString()) {
                    setError(PASSWORD_NOT_MATCH)
                    setErrorPasswordNotMatch(PASSWORD_NOT_MATCH)
            } else {
                removeError()
                removeErrorPasswordNotMatch()
            }
        }

        /**
         * Realiza a validacao para o tipo do campo
         * @param inputType Int - tipo do campo
         * @return Boolean - false se tiver erro
         */
        private fun validationForType(inputType: Int): Boolean {
            var result = true
            when(inputType) {
                EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + 1 -> {
                    // como referencia, outra forma de validar o email
                    // text.toString().matches("^([\\w\\.])+@([a-zA-Z0-9])+(\\.([a-zA-Z])+)+$".toRegex())
                    val fullRegex = android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
                    result = if (value.isNotEmpty() && !fullRegex || required && value.isEmpty() && !fullRegex) {
                        setError(ERROR_EMAIL)
                    } else { removeError() }
                }
            }
            return result
        }

        /**
         * Exibe o erro
         * @return Boolean - false se tiver erro
         */
        private fun setError(i: Int): Boolean {
            inputLayout?.let {
                it.isErrorEnabled = true
                it.errorIconDrawable = null
                it.error = getErrorMessage(i)
            }
            return false // com erro
        }

        /**
         * Exibe o erro no campo que nao combina
         * @return Boolean - false se tiver erro
         */
        private fun setErrorPasswordNotMatch(i: Int): Boolean {
            inputLayoutForMatch?.let {
                it.isErrorEnabled = true
                it.errorIconDrawable = null
                it.error = getErrorMessage(i)
            }
            return false // com erro
        }

        /**
         * Remove o erro
         * @return Boolean - true se remover o erro
         */
        private fun removeError(): Boolean {
            inputLayout?.let { it.isErrorEnabled = false }
            return true // sem erro
        }

        /**
         * Remove o erro no campo que nao combina
         * @return Boolean - true se remover o erro
         */
        private fun removeErrorPasswordNotMatch(): Boolean {
            inputLayoutForMatch?.let { it.isErrorEnabled = false }
            return true // sem erro
        }

        /**
         * Obter a mensagem de erro
         * @param type Int - tipo do erro
         * @return String - mensagem de erro
         */
        private fun getErrorMessage(type: Int): String {
            return when(type) {
                ERROR_EMPTY -> {
                    activity.getString(R.string.ih_field_empty_error)
                }
                ERROR_EMAIL -> {
                    activity.getString(R.string.ih_email_error)
                }
                ERROR_MIN -> {
                    activity.getString(R.string.ih_field_min_error, (min - 1).toString())
                }
                ERROR_LENGTH -> {
                    activity.getString(R.string.ih_field_length_error, minLength.toString())
                }
                PASSWORD_NOT_MATCH -> {
                    activity.getString(R.string.ih_password_not_match)
                }
                else -> activity.getString(R.string.ih_default_error)
            }
        }
    }
}
