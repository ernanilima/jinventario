package br.com.ernanilima.jinventario.firebase;

import android.content.Context;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

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
        this.usuarioAtual = autenticacao.getCurrentUser();
    }

    @Override
    /** Verifica se o usuario estah autenticado/logado
     * Se estiver, regista no resultado */
    public void verificarSeUsuarioAutenticado() {
        if (usuarioAtual != null) {
            iResFirebase.setResultado(TipoResultado.LOGIN_REALIZADO);
        }
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

    @Override
    /** Realiza login no firebase e cadastra credenciais de usuario que
     * realizou login com o google */
    public void loginUsuarioGoogle(String idTokenUsuario) {
        AuthCredential credencial = GoogleAuthProvider.getCredential(idTokenUsuario, null);
        autenticacao.signInWithCredential(credencial)
                .addOnCompleteListener(aut -> {
                    if (aut.isSuccessful()) {
                        usuarioAtual = autenticacao.getCurrentUser();
                        iResFirebase.setResultado(TipoResultado.LOGIN_REALIZADO);
                    }
                });
    }

    @Override
    public void loginUsuario(Context contextTelaDoErro, String email, String senha) {
        autenticacao.signInWithEmailAndPassword(email, senha).addOnCompleteListener(aut -> {
            if (aut.isSuccessful()) {
                usuarioAtual = autenticacao.getCurrentUser();
                iResFirebase.setResultado(TipoResultado.LOGIN_REALIZADO);
            } else {
                // erro no login de usuario
                System.out.println("ERRO NO LOGIN DE USUARIO COM SENHA, CODIGO: " +
                        ((FirebaseAuthException) aut.getException()).getErrorCode());
            }
        });
    }
}
