package br.com.ernanilima.jinventario.firebase;

import com.google.firebase.auth.FirebaseAuth;

import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;
import br.com.ernanilima.jinventario.firebase.interfaces.IFirebase;
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
    public void cadastrarUsuario(String email, String senha) {
        autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(aut -> {
            if (aut.isSuccessful()) {
                iResFirebase.setResultado(TipoResultado.CADASTRO_REALIZADO);
            }
        });
    }
}
