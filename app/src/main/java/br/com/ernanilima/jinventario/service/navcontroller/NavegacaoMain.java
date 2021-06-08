package br.com.ernanilima.jinventario.service.navcontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import br.com.ernanilima.jinventario.MainActivity;
import br.com.ernanilima.jinventario.R;

/** Navegacoes na activity main
 * Activity das telas associadas ao login */
public class NavegacaoMain {

    private static NavController navController;

    /** Abre a activity {@link MainActivity}
     * @param context Context - context(tela) que esta solicitando */
    public static void abrirTelaActivityMain(Context context) {
        Activity activity = (Activity)context;
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /** Navega para a tela solicitada utilizando o xml de navegacao
     * @param view View - view(tela) que esta solicitando */
    public static void abrirTelaLogin(View view) {
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.telaLogin);
    }

    /** Navega para a tela solicitada utilizando o xml de navegacao
     * @param view View - view(tela) que esta solicitando */
    public static void abrirTelaCadastrar(View view) {
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.telaCadastro);
    }

    /** Navega para a tela solicitada utilizando o xml de navegacao
     * @param view View - view(tela) que esta solicitando */
    public static void abrirTelaEsqueceuSenha(View view) {
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.telaEsqueceuSenha);
    }
}
