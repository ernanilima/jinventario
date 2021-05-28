package br.com.ernanilima.jinventario.firebase.interfaces;

import br.com.ernanilima.jinventario.firebase.Firebase;

/** Interface usada para realizar as autenticacoes do firebase
 * usadi na classe {@link Firebase} */
public interface IFirebase {
    void cadastrarUsuario(String email, String senha);
}
