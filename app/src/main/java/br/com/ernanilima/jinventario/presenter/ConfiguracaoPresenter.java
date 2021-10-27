package br.com.ernanilima.jinventario.presenter;


import br.com.ernanilima.jinventario.BaseApplication;
import br.com.ernanilima.jinventario.interfaces.IConfiguracao;
import br.com.ernanilima.jinventario.model.Configuracao;
import br.com.ernanilima.jinventario.repository.orm.ConfiguracaoDao;
import br.com.ernanilima.jinventario.repository.orm.DaoSession;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp;

public class ConfiguracaoPresenter implements IConfiguracao.IPresenter {

    private IConfiguracao.IView vConfiguracao;
    private DaoSession daoSession;
    private ConfiguracaoDao dConfiguracao;
    private Configuracao dbConfiguracao;

    /** Construtor
     * @param vConfiguracao IConfiguracao.IView - view(fragment) de configuracao */
    public ConfiguracaoPresenter(IConfiguracao.IView vConfiguracao) {
        this.vConfiguracao = vConfiguracao;

        // GREENDAO
        this.daoSession = ((BaseApplication) this.vConfiguracao.requireParentFragment().getActivity().getApplication()).getSessao();
        this.dConfiguracao = daoSession.getConfiguracaoDao();

        // BUSCA CONFIGURACAO GRAVADA NO BANCO
         dbConfiguracao = dConfiguracao.load(1L);
    }

    @Override
    /** Grava as configuracoes */
    public void gravarConfiguracao() {
        // atribui na configuracao criada, uma configuracao do banco ou um nova
        Configuracao mConfiguracao = (dbConfiguracao != null) ? dbConfiguracao : new Configuracao();

        // atribui boolean para informar preco do produto coletado
        mConfiguracao.setInformarPreco(vConfiguracao.getConfigInformarPreco());
        // atribui boolean para usar camera como scanner
        mConfiguracao.setCameraScanner(vConfiguracao.getConfigCameraScanner());
        // atribui boolean para usar o tipo de camera ml kit (google)
        mConfiguracao.setCameraScannerMlkit(vConfiguracao.getConfigUsarCameraMlkit());
        // atribui boolean para usar o tipo de camera zxing
        mConfiguracao.setCameraScannerZxing(vConfiguracao.getConfigUsarCameraZxing());

        // grava a configuracao
        dConfiguracao.save(mConfiguracao);

        // abre a tela inicial do app
        NavegacaoApp.abrirTelaInicioApp(vConfiguracao.requireParentFragment().getView());
    }

    @Override
    /** Atribui no campo as configuracoes gravadas no banco */
    public void popularDadosConfiguracao() {
        if (dbConfiguracao != null) {
            vConfiguracao.setConfigInformarPreco(dbConfiguracao.getInformarPreco());
            vConfiguracao.setConfigCameraScanner(dbConfiguracao.getCameraScanner());
            vConfiguracao.setConfigUsarCameraMlkit(dbConfiguracao.getCameraScannerMlkit());
            vConfiguracao.setConfigUsarCameraZxing(dbConfiguracao.getCameraScannerZxing());
        }
    }
}
