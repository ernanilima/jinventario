package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import br.com.ernanilima.jinventario.interfaces.IEsqueceuSenha;
import br.com.ernanilima.jinventario.service.constant.MensagemAlerta;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;

public class EsqueceuSenhaPresenter  implements IEsqueceuSenha.EsqueceuSenhaPresenter {

    private IEsqueceuSenha.EsqueceuSenhaView vEsqueceuSenha;

    /** Construtor
     * @param vEsqueceuSenha IEsqueceuSenha.EsqueceuSenhaView - view(activity) de esqueceu senha */
    public EsqueceuSenhaPresenter(IEsqueceuSenha.EsqueceuSenhaView vEsqueceuSenha) {
        this.vEsqueceuSenha = vEsqueceuSenha;
    }

    @Override
    public void gerarNovaSenha(View view) {
        if (validarCampos()) {
            System.out.println("envia e-mail");
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vEsqueceuSenha.getCampoEmail(), MensagemAlerta.EMAIL_INVALIDO);
    }
}
