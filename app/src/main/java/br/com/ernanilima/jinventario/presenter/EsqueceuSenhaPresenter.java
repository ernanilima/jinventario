package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import br.com.ernanilima.jinventario.firebase.Firebase;
import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;
import br.com.ernanilima.jinventario.firebase.interfaces.IFirebase;
import br.com.ernanilima.jinventario.interfaces.IEsqueceuSenha;
import br.com.ernanilima.jinventario.service.constant.MensagemAlerta;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;

public class EsqueceuSenhaPresenter implements IEsqueceuSenha.EsqueceuSenhaPresenter {

    private IEsqueceuSenha.EsqueceuSenhaView vEsqueceuSenha;
    private IFirebase iFirebase;

    /** Construtor
     * @param vEsqueceuSenha IEsqueceuSenha.EsqueceuSenhaView - view(activity) de esqueceu senha */
    public EsqueceuSenhaPresenter(IEsqueceuSenha.EsqueceuSenhaView vEsqueceuSenha) {
        this.vEsqueceuSenha = vEsqueceuSenha;
        iFirebase = new Firebase(this);
    }

    @Override
    public void gerarNovaSenha(View view) {
        if (validarCampo()) {
            String email = vEsqueceuSenha.getCampoEmail().getEditText().getText().toString();
            iFirebase.enviarEmailEsqueceuSenha(email);
        }
    }

    private boolean validarCampo() {
        return ValidarCampo.vazio(vEsqueceuSenha.getCampoEmail(), MensagemAlerta.EMAIL_INVALIDO);
    }

    @Override
    public void setResultado(TipoResultado resultado) {
        switch (resultado) {
            case EMAIL_NOVA_SENHA_ENVIADO:
                System.out.println("e-mail esqueceu senha enviado");
        }
    }
}
