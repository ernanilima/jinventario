package br.com.ernanilima.jinventario.service.navcontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import br.com.ernanilima.jinventario.AppActivity;
import br.com.ernanilima.jinventario.R;

/** Classe criada para agrupar as navegacoes */
public class Navegacao {

    private static NavController navController;

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

    /** Abre tela de activity principal
     * @param context Context - context(tela) que esta solicitando */
    public static void abrirTelaActivityPrincipal(Context context) {
        Activity activity = (Activity)context;
        Intent intent = new Intent(activity, AppActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
