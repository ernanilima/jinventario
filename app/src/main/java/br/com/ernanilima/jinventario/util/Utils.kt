package br.com.ernanilima.jinventario.util

import android.view.View
import android.widget.EditText
import android.view.ViewGroup.MarginLayoutParams
import com.google.android.material.textfield.TextInputLayout

object Utils {

    /**
     * Limpa os campos passados no parametro
     * @param node Ant - campos
     */
    fun clearFields(vararg node: Any) {
        for (nodes in node) {
            val lsNodes = listOf(nodes)
            for (n in lsNodes) {
                if (n is EditText) n.setText("")
            }
        }
    }

    /**
     * Limpa os erros dos layouts passados no parametro
     * @param node Ant - layouts
     */
    fun clearErrors(vararg node: Any) {
        for (nodes in node) {
            val lsNodes = listOf(nodes)
            for (n in lsNodes) {
                if (n is TextInputLayout) n.isErrorEnabled = false
            }
        }
    }

    /**
     * Converte o e-mail do usuario para que seja aceito no firebase
     * @param email String - e-mail do usuario
     * @return String - e-mail do usuario convertido
     */
    fun convert(email: String): String {
        // realtime databese do firebase nao aceita '.', '#', '$', '[', ou ']'
        // abaixo o e-mail eh convertido para que seja aceito e sirva para gravar
        // o e-mail como chave no banco do firebase
        return email
            .replace(".", "P") // P = ponto
            .replace("#", "H") // H = hashtag
            .replace("$", "D") // D = dolar
            .replace("[", "AC") // AC = abre colchete
            .replace("]", "FC") // FC = fecha colchete
    }

    /**
     * Desabilitar funcao exibida no xml
     * @param view View - campo/view para desabilitar
     */
    fun disableOption(view: View) {
        view.visibility = View.INVISIBLE
        // largura do campo, nao pode ser 0(zero)
        view.layoutParams.width = 1
        view.layoutParams.height = 1
        // margem do campo
        val paramsField = view.layoutParams as MarginLayoutParams
        paramsField.topMargin = 0
        paramsField.rightMargin = 0
        paramsField.bottomMargin = 0
        paramsField.leftMargin = 0
    }
}
