package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.interfaces.IConfiguracao;
import br.com.ernanilima.jinventario.model.Configuracao;
import br.com.ernanilima.jinventario.model.ConfiguracaoDao;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp;

public class ConfiguracaoPresenter implements IConfiguracao.IPresenter {

    private IConfiguracao.IView vConfiguracao;
    private DaoSession daoSession;
    private ConfiguracaoDao dConfiguracao;

    /** Construtor
     * @param vConfiguracao IConfiguracao.IView - view(fragment) de configuracao */
    public ConfiguracaoPresenter(IConfiguracao.IView vConfiguracao) {
        this.vConfiguracao = vConfiguracao;

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vConfiguracao.requireParentFragment().getActivity().getApplication()).getSessao();
        this.dConfiguracao = daoSession.getConfiguracaoDao();
    }

    @Override
    public void gravarConfiguracao() {
        Configuracao dbConfiguracao = dConfiguracao.load(1L); // busca configuracao no banco greendao
        Configuracao mConfiguracao = (dbConfiguracao != null) ? dbConfiguracao : new Configuracao(); // verifica se busca eh null
        mConfiguracao.setCameraScanner(vConfiguracao.getConfigCameraScanner()); // atribui boolean da opcao de configuracao
        dConfiguracao.save(mConfiguracao); // grava a configuracao
        NavegacaoApp.abrirTelaInicioApp(vConfiguracao.requireParentFragment().getView()); // abre a tela inicial do app
    }
}
