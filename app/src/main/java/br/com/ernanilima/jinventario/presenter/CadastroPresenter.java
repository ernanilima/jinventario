package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import br.com.ernanilima.jinventario.interfaces.ICadastro;

public class CadastroPresenter implements ICadastro.CadastroPresenter {

    private ICadastro.CadastroView vCadastro;

    /** Construtor
     * @param vCadastro ICadastro.CadastroView - view(activity) de cadastro */
    public CadastroPresenter(ICadastro.CadastroView vCadastro) {
        this.vCadastro = vCadastro;
    }

    @Override
    public void cadastrar(View view) {
        System.out.println("cadastrou");
    }
}
