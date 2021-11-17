package br.com.ernanilima.jinventario.extension.common.snackbar;

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater;
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.databinding.SnackbarCustomBinding
import com.google.android.material.snackbar.Snackbar

object SnackbarCustom {

    /**
     * @param context Context - onde sera exibido
     * @param message String - mensagem para exibir
     */
    fun success(fragment: Fragment, message: String) {
        base(fragment, R.drawable.bg_success, message)
    }

    /**
     * @param context Context - onde sera exibido
     * @param message String - mensagem para exibir
     */
    fun warning(fragment: Fragment, message: String) {
        base(fragment, R.drawable.bg_warning, message)
    }

    //fun error(context: Context, message: String) {
    //    base(context, R.drawable.toast_error, message)
    //}

    /**
     * @param context Context - onde sera exibido
     * @param type Int - background
     * @param message String - mensagem para exibir
     */
    @SuppressLint("ShowToast")
    private fun base(fragment: Fragment, type: Int, message: String) {
        val layoutInflater: LayoutInflater = LayoutInflater.from(fragment.requireContext())
        val binding: SnackbarCustomBinding = SnackbarCustomBinding.inflate(layoutInflater)
        binding.toastRoot.background = ContextCompat.getDrawable(fragment.requireContext(), type)
        binding.txtMessage.text = message
        val snackbar: Snackbar = Snackbar.make(fragment.requireView(), "", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout: Snackbar.SnackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        snackbarLayout.addView(binding.root)
        snackbar.show()
    }
}
