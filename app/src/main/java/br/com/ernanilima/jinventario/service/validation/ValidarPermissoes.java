package br.com.ernanilima.jinventario.service.validation;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class ValidarPermissoes {

    /** Exibe o dialog do android para solicitar as permissoes para o aplicativo
     * As mesmas permissoes solicitadas devem estar no manifest.xml
     * @param activity Activity - activity para exibir o dialog de solicitacao
     * @param permissoes String[] - permissoes para solicitar */
    public static void permissoes(Activity activity, String[] permissoes) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // se a api do aparelho for igual ou maior que 23

            // cria lista de permissoes
            List<String> lstPermissoes = new ArrayList<>();
            for (String permissao : permissoes) {

                // verifica se a permissao ja foi dada
                boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;

                // se a permissao nao foi dada, adiciona a permissao na lista
                if (!temPermissao) { lstPermissoes.add(permissao); }
            }

            // verifica se a lista esta vazia, significa que todas as permissoes foram dadas
            if (lstPermissoes.isEmpty()) { return; }

            String[] novasPermissoes = new String[lstPermissoes.size()];
            lstPermissoes.toArray(novasPermissoes);

            ActivityCompat.requestPermissions(activity, novasPermissoes, 1);
        }
    }
}
