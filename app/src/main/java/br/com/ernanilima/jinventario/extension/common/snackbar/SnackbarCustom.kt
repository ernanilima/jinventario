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
     * @param message String - mensagem para exibir
     */
    fun success(context: Context, message: String) {
        base(context, R.drawable.background_success, message)
    }

    /**
     * @param context Context - onde sera exibido
     * @param message String - mensagem para exibir
     */
    fun warning(context: Context, message: String) {
        base(context, R.drawable.background_warning, message)
    }

    //fun error(context: Context, message: String) {
    //    base(context, R.drawable.toast_error, message)
    //}

    /**
     * @param context Context - onde sera exibido
     * @param type Int - background
     * @param message String - mensagem para exibir
     */
    private fun base(context: Context, type: Int, message: String) {
        // busca e atribui os valores no layout personalizado
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val binding: SnackbarCustomBinding = SnackbarCustomBinding.inflate(layoutInflater)
        binding.toastRoot.background = ContextCompat.getDrawable(context, type)
        binding.txtMessage.text = message

        if (context is Activity) {
            baseSnackbar(context, binding.root)
        } else {
            baseToast(context, binding.root)
        }
    }

    /**
     * Usado como padrao, oculta o teclado e exibe um snackbar
     * @param context Context - onde sera exibido
     * @param bindingView View - layout personalizado
     */
    private fun baseSnackbar(context: Context, bindingView: View) {
        // oculta o teclado
        DeviceHelper.hideKeyboard(context)

        // constroi o snackbar
        val snackbar: Snackbar = Snackbar.make((context as Activity).window.decorView.rootView, "", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)

        // constroi o layout do snackbar, adiciona o snackbar personalizado e exibe
        val snackbarLayout: Snackbar.SnackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        snackbarLayout.addView(bindingView)
        snackbar.show()
    }

    /**
     * Usado quando o snackbar nao pode ser exibido
     * @param context Context - onde sera exibido
     * @param bindingView View - layout personalizado
     */
    private fun baseToast(context: Context, bindingView: View) {
        // constroi e exibe o toast
        val toast = Toast(context)
        toast.view = bindingView
        toast.duration = Toast.LENGTH_LONG
        toast.show()
    }
}
