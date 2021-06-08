package br.com.ernanilima.jinventario.service.navcontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import br.com.ernanilima.jinventario.NomeAparelhoActivity;

/** Navegacoes para a activity nome do aparelho */
public class NavegacaoNomeAparelho {

    /** Abre a activity {@link NomeAparelhoActivity}
     * @param context Context - context(tela) que esta solicitando */
    public static void abrirTelaActivityNomeAparelho(Context context) {
        Activity activity = (Activity)context;
        Intent intent = new Intent(activity, NomeAparelhoActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
