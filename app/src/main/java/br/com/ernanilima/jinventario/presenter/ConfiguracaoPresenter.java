package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.interfaces.IConfiguracao;

public class ConfiguracaoPresenter implements IConfiguracao.IPresenter {

    private IConfiguracao.IView vConfiguracao;

    /** Construtor
     * @param vConfiguracao IConfiguracao.IView - view(fragment) de configuracao */
    public ConfiguracaoPresenter(IConfiguracao.IView vConfiguracao) {
        this.vConfiguracao = vConfiguracao;
    }

    @Override
    public void gravarConfiguracao() {
        System.out.println("OPCAO NA CONFIGURACAO EH " + vConfiguracao.getConfigCameraScanner());
    }
}
