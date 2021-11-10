package br.com.ernanilima.jinventario.service.navcontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import br.com.ernanilima.jinventario.ui.MainActivity;

/** Navegacoes na activity main
 * Activity das telas associadas ao login */
@Deprecated
public class NavegacaoMain {

    /** Abre a activity {@link MainActivity}
     * @param context Context - context(tela) que esta solicitando */
    @Deprecated
    public static void abrirTelaActivityMain(Context context) {
        Activity activity = (Activity)context;
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
