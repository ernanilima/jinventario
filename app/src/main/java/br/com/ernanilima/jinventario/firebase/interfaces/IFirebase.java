package br.com.ernanilima.jinventario.firebase.interfaces;

import android.content.Context;

import br.com.ernanilima.jinventario.firebase.Firebase;

/** Interface usada para realizar as autenticacoes do firebase
 * usado na classe {@link Firebase} */
public interface IFirebase {
    void verificarSeUsuarioAutenticado();
    String getEmailUsuario();
    void cadastrarUsuario(Context contextTelaDoErro, String email, String senha);
    void enviarEmailVerificacao(Context contextTelaDoErro);
    void enviarEmailEsqueceuSenha(Context contextTelaDoErro, String email);
    void loginUsuarioGoogle(String idTokenUsuario);
    void loginUsuario(Context contextTelaDoErro, String email, String senha);
}
