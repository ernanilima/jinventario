package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import br.com.ernanilima.jinventario.interfaces.ICadastro;
import br.com.ernanilima.jinventario.validation.ValidarCampo;

public class CadastroPresenter implements ICadastro.CadastroPresenter {

    private ICadastro.CadastroView vCadastro;

    /** Construtor
     * @param vCadastro ICadastro.CadastroView - view(activity) de cadastro */
    public CadastroPresenter(ICadastro.CadastroView vCadastro) {
        this.vCadastro = vCadastro;
    }

    @Override
    public void cadastrar(View view) {
        if (validarCampos()) {
            System.out.println("cadastrou");
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vCadastro.getCampoEmail(), "Informe o e-mail") &&
                ValidarCampo.vazio(vCadastro.getCampoSenha1(), "Informe a senha") &&
                ValidarCampo.vazio(vCadastro.getCampoSenha2(), "Informe a senha novamente") &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha1(), 6) &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha2(), 6) &&
                ValidarCampo.senhasIguais(vCadastro.getCampoSenha1(), vCadastro.getCampoSenha2(), "Senhas não combinam");
    }
}
