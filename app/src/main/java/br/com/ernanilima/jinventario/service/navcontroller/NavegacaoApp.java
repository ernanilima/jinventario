package br.com.ernanilima.jinventario.service.navcontroller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import br.com.ernanilima.jinventario.AppActivity;
import br.com.ernanilima.jinventario.R;

/** Navegacoes na activity app
 * Activity das telas associadas ao inicio do aplicativo */
public class NavegacaoApp {

    private static NavController navController;

    /** Abre a activity {@link AppActivity}
     * @param context Context - context(tela) que esta solicitando */
    public static void abrirTelaActivityApp(Context context) {
        //Activity activity = (Activity)context;
        Intent intent = new Intent(context, AppActivity.class);
        context.startActivity(intent);
        //activity.finish(); executado na solicitacao da navegacao
    }

    /** Navega para a tela solicitada utilizando o xml de navegacao
     * @param view View - view(tela) que esta solicitando */
    public static void abrirTelaInicioApp(View view) {
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.nav_inicio);
    }

    /** Navega para a tela solicitada utilizando o xml de navegacao
     * @param view View - view(tela) que esta solicitando */
    public static void abrirTelaContagem(View view) {
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.nav_contagem);
    }
}
