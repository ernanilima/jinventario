package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import br.com.ernanilima.jinventario.interfaces.ICadastro;
import br.com.ernanilima.jinventario.service.constant.MensagemAlerta;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;

public class CadastroPresenter implements ICadastro.CadastroPresenter {

    private ICadastro.CadastroView vCadastro;
    private FirebaseAuth auth;

    /** Construtor
     * @param vCadastro ICadastro.CadastroView - view(activity) de cadastro */
    public CadastroPresenter(ICadastro.CadastroView vCadastro) {
        this.vCadastro = vCadastro;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void cadastrar(View view) {
        if (validarCampos()) {
            String email = vCadastro.getCampoEmail().getEditText().getText().toString();
            String senha = vCadastro.getCampoSenha1().getEditText().getText().toString();

            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(aut -> {
                if (aut.isSuccessful()) {
                    System.out.println("cadastrou");
                }
            });
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vCadastro.getCampoEmail(), MensagemAlerta.EMAIL_INVALIDO) &&
                ValidarCampo.vazio(vCadastro.getCampoSenha1(), MensagemAlerta.SENHA_INVALIDA) &&
                ValidarCampo.vazio(vCadastro.getCampoSenha2(), MensagemAlerta.SENHA_INVALIDA) &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha1(), 6) &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha2(), 6) &&
                ValidarCampo.senhasIguais(vCadastro.getCampoSenha1(), vCadastro.getCampoSenha2(), MensagemAlerta.SENHAS_NAO_COMBINAM);
    }
}
