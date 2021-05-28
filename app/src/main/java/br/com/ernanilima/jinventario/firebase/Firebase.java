package br.com.ernanilima.jinventario.firebase;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;
import br.com.ernanilima.jinventario.firebase.interfaces.IFirebase;
import br.com.ernanilima.jinventario.firebase.validation.ValidarFirebase;
import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase;

public class Firebase implements IFirebase {

    private FirebaseAuth autenticacao;
    private IResultadoFirebase iResFirebase;

    public Firebase(IResultadoFirebase iResFirebase) {
        this.autenticacao = FirebaseAuth.getInstance();
        this.iResFirebase = iResFirebase;
    }

    @Override
    /** Cadastra um novo usuario com email e senha */
    public void cadastrarUsuario(Context contextTelaDoErro, String email, String senha) {
        autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(aut -> {
            if (aut.isSuccessful()) {
                iResFirebase.setResultado(TipoResultado.CADASTRO_REALIZADO);
            } else {
                // erro no cadastro
                ValidarFirebase.erroCadastro(contextTelaDoErro, ((FirebaseAuthException) aut.getException()).getErrorCode());
            }
        });
    }
}
