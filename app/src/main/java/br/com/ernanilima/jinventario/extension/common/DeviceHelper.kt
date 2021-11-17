package br.com.ernanilima.jinventario.extension.common

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment


object DeviceHelper {

    /**
     * Valida a conexao com a internet
     * @param context Context
     * @return boolean - true se tiver internet
     */
    fun isInternet(context: Context?): Boolean {
        val connection = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // validacao para aparelhos novos
            val capabilities = connection.getNetworkCapabilities(connection.activeNetwork)

            // retorna true se capabilities nao for nulo e tiver internet do celular ou do wifi
            // caso nao tenha, retorna false
            capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
        } else {
            // validacao para aparelhos antigos
            val info = connection.activeNetworkInfo

            // retorna true se info nao for nulo e o aparelho tiver internet
            // caso nao tenha, retorna false
            info != null && info.isConnectedOrConnecting
        }
    }

    /**
     * Oculta o teclado
     * @param context Context
     */
    fun hideKeyboard(context: Context) {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow((context as Activity).window.decorView.rootView.windowToken, 0)
    }
}