package br.com.ernanilima.jinventario.firebase;

import android.content.Context;

/** Interface usada para realizar as autenticacoes do firebase
 * usado na classe {@link FirebaseAutenticacao} */
public interface IFirebaseAutenticacao {
    void verificarSeUsuarioAutenticado();
    String getEmailUsuario();
    void cadastrarUsuario(Context contextTelaDoErro, String email, String senha);
    void enviarEmailVerificacao(Context contextTelaDoErro);
    void enviarEmailEsqueceuSenha(Context contextTelaDoErro, String email);
    void loginUsuarioGoogle(String idTokenUsuario);
    void loginUsuario(Context contextTelaDoErro, String email, String senha);
}
