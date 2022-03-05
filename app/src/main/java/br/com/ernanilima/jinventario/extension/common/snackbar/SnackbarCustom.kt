package br.com.ernanilima.jinventario.extension.common.snackbar;

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater;
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.databinding.SnackbarCustomBinding
import com.google.android.material.snackbar.Snackbar
import br.com.ernanilima.jinventario.extension.common.DeviceHelper

object SnackbarCustom {

    /**
     * @param context Context - onde sera exibido
     * @param view View - onde sera exibido
     * @param message String - mensagem para exibir
     */
    fun success(context: Context, view: View, message: String) {
        base(context, view, R.drawable.background_success, message)
    }

    /**
     * @param context Context - onde sera exibido
     * @param view View - onde sera exibido
     * @param message String - mensagem para exibir
     */
    fun warning(context: Context, view: View, message: String) {
        base(context, view, R.drawable.background_warning, message)
    }

    //fun error(context: Context, view: View, message: String) {
    //    base(context, view, R.drawable.toast_error, message)
    //}

    /**
     * @param context Context - onde sera exibido
     * @param view View - onde sera exibido
     * @param type Int - background
     * @param message String - mensagem para exibir
     */
    private fun base(context: Context, view: View, type: Int, message: String) {
        // busca e atribui os valores no layout personalizado
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val bindingSnackbar: SnackbarCustomBinding = SnackbarCustomBinding.inflate(layoutInflater)
        bindingSnackbar.toastRoot.background = ContextCompat.getDrawable(context, type)
        bindingSnackbar.txtMessage.text = message

        if (context is Activity) {
            baseSnackbar(context, view, bindingSnackbar.root)
        } else {
            baseToast(context, bindingSnackbar.root)
        }
    }

    /**
     * Usado como padrao, oculta o teclado e exibe um snackbar
     * @param context Context - onde sera exibido
     * @param view View - onde sera exibido
     * @param bindingSnackbar View - layout personalizado
     */
    private fun baseSnackbar(context: Context, view: View, bindingSnackbar: View) {
        // oculta o teclado
        DeviceHelper.hideKeyboard(context)

        // constroi o snackbar
        val snackbar: Snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)

        // constroi o layout do snackbar, adiciona o snackbar personalizado e exibe
        val snackbarLayout: Snackbar.SnackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        snackbarLayout.addView(bindingSnackbar)
        snackbar.show()
    }

    /**
     * Usado quando o snackbar nao pode ser exibido
     * @param context Context - onde sera exibido
     * @param bindingSnackbar View - layout personalizado
     */
    private fun baseToast(context: Context, bindingSnackbar: View) {
        // constroi e exibe o toast
        val toast = Toast(context)
        toast.view = bindingSnackbar
        toast.duration = Toast.LENGTH_LONG
        toast.show()
    }
}
