package br.com.ernanilima.jinventario.firebase;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;
import br.com.ernanilima.jinventario.firebase.interfaces.IFirebase;
import br.com.ernanilima.jinventario.firebase.validation.ValidarFirebase;
import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase;

public class Firebase implements IFirebase {

    private FirebaseAuth autenticacao;
    private FirebaseUser usuarioAtual;
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
                usuarioAtual = autenticacao.getCurrentUser();
                iResFirebase.setResultado(TipoResultado.CADASTRO_REALIZADO);
            } else {
                // erro no cadastro
                ValidarFirebase.erroCadastro(contextTelaDoErro, ((FirebaseAuthException) aut.getException()).getErrorCode());
            }
        });
    }

    @Override
    /** Envia e-mail de verificacao para usuario
     * confirmar que e-mail cadastrado existe */
    public void enviarEmailVerificacao() {
        usuarioAtual.sendEmailVerification().addOnCompleteListener(aut -> {
            if (aut.isSuccessful()) {
                iResFirebase.setResultado(TipoResultado.EMAIL_VERIFICACAO_ENVIADO);

            } else {
                // erro no envio do e-mail de verificacao
                System.out.println("ERRO NO ENVIO DE EMAIL DE VERIFICACAO, CODIGO: " +
                        ((FirebaseAuthException) aut.getException()).getErrorCode());
            }
        });
    }

    @Override
    /** Envia e-mail para redefinir a senha do usuario */
    public void enviarEmailEsqueceuSenha(String email) {
        autenticacao.sendPasswordResetEmail(email).addOnCompleteListener(aut -> {
            if (aut.isSuccessful()) {
                iResFirebase.setResultado(TipoResultado.EMAIL_NOVA_SENHA_ENVIADO);

            } else {
                // erro no envio do e-mail de esqueceu a senha
                System.out.println("ERRO NO ENVIO DE EMAIL DE ESQUECEU A SENHA, CODIGO: " +
                        ((FirebaseAuthException) aut.getException()).getErrorCode());
            }
        });
    }
}
