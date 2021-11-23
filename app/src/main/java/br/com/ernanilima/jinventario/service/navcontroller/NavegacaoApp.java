package br.com.ernanilima.jinventario.service.navcontroller;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import br.com.ernanilima.jinventario.R;

/** Navegacoes na activity app
 * Activity das telas associadas ao inicio do aplicativo */
public class NavegacaoApp {

    private static NavController navController;

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

    /** Navega para a tela solicitada utilizando o xml de navegacao
     * @param view View - view(tela) que esta solicitando */
    public static void abrirTelaConfiguracao(View view) {
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.nav_configuracao);
    }
}
