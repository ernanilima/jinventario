package br.com.ernanilima.jinventario.service.navcontroller;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import br.com.ernanilima.jinventario.R;

/** Classe criada para agrupar as navegacoes */
public class Navegacao {

    private static NavController navController;

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
