package br.com.ernanilima.jinventario.firebase.interfaces;

import android.content.Context;

import br.com.ernanilima.jinventario.firebase.Firebase;

/** Interface usada para realizar as autenticacoes do firebase
 * usadi na classe {@link Firebase} */
public interface IFirebase {
    void cadastrarUsuario(Context contextTelaDoErro, String email, String senha);
    void enviarEmailVerificacao();
    void enviarEmailEsqueceuSenha(String email);
}
