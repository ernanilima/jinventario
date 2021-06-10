package br.com.ernanilima.jinventario.service.validation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class ValidarInternet {

    /** Valida a conexao com a internet
     * @param context Context - context da tela onde ocorreu o erro
     * @return boolean - true se tiver internet */
    public static boolean conexao(Context context) {

        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conexao != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // validacao para aparelhos novos
                NetworkCapabilities capabilities = conexao.getNetworkCapabilities(conexao.getActiveNetwork());

                // retorna true se capabilities nao for nulo e tiver internet do celular ou do wifi
                // caso nao tenha, retorna false
                return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));

            } else {
                // validacao para aparelhos antigos
                NetworkInfo info = conexao.getActiveNetworkInfo();

                // retorna true se info nao for nulo e o aparelho tiver internet
                // caso nao tenha, retorna false
                return info != null && info.isConnectedOrConnecting();
            }
        }

        // se chegar aqui, deu ruim
        return false;
    }
}
