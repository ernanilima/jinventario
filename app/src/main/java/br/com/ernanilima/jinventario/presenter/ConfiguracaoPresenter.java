package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.interfaces.IConfiguracao;
import br.com.ernanilima.jinventario.model.Configuracao;
import br.com.ernanilima.jinventario.dao.ConfiguracaoDao;
import br.com.ernanilima.jinventario.dao.DaoSession;

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
        System.out.println("USAR CAMERA SCANNER MLKIT " + vConfiguracao.getConfigUsarCameraMlkit());
        System.out.println("USAR CAMERA SCANNER ZXING " + vConfiguracao.getConfigUsarCameraZxing());
//        Configuracao dbConfiguracao = dConfiguracao.load(1L); // busca configuracao no banco greendao
//        Configuracao mConfiguracao = (dbConfiguracao != null) ? dbConfiguracao : new Configuracao(); // verifica se busca eh null
//        mConfiguracao.setCameraScanner(vConfiguracao.getConfigCameraScanner()); // atribui boolean da opcao de configuracao
//        dConfiguracao.save(mConfiguracao); // grava a configuracao
//        NavegacaoApp.abrirTelaInicioApp(vConfiguracao.requireParentFragment().getView()); // abre a tela inicial do app
    }

    @Override
    /** Usado para buscar a configuracao no banco greendao e atribuir essa configuracao no campo */
    public void popularDados() {
        Configuracao dbConfiguracao = dConfiguracao.load(1L); // busca configuracao no banco greendao
        if (dbConfiguracao != null) {
            vConfiguracao.setConfigCameraScanner(dbConfiguracao.getCameraScanner());
        }
    }
}
