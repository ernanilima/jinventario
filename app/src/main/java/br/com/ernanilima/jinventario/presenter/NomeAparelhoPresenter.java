package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.interfaces.INomeAparelho;
import br.com.ernanilima.jinventario.dao.DaoSession;
import br.com.ernanilima.jinventario.model.NomeAparelho;
import br.com.ernanilima.jinventario.dao.NomeAparelhoDao;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;

public class NomeAparelhoPresenter implements INomeAparelho.IPresenter {

    private INomeAparelho.IView vNomeAparelho;
    private DaoSession daoSession;
    private NomeAparelhoDao dNomeAparelho;

    /** Construtor
     * @param vNomeAparelho INomeAparelho.IView - view(fragment) do nome do aparelho */
    public NomeAparelhoPresenter(INomeAparelho.IView vNomeAparelho) {
        this.vNomeAparelho = vNomeAparelho;

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vNomeAparelho.getApplication()).getSessao();
        this.dNomeAparelho = daoSession.getNomeAparelhoDao();
    }

    @Override
    public void gravarNomeDoAparelho() {
        if (validarCampo()) {
            // gera um model com o nome do aparelho
            NomeAparelho mNomeAparelho = new NomeAparelho(null, vNomeAparelho.getCampoNomeAparelho().getEditText().getText().toString());

            // grava o model
            dNomeAparelho.save(mNomeAparelho);

            // navega para a tela de login
            NavegacaoApp.abrirTelaActivityApp(vNomeAparelho.getApplication().getBaseContext());

            // finaliza a view para informar o nome do aparelho
            vNomeAparelho.finish();
        }
    }

    private boolean validarCampo() {
        return ValidarCampo.vazio(vNomeAparelho.getCampoNomeAparelho(), MensagensAlerta.NOME_APARELHO_INVALIDO.getMsg()) &&
                ValidarCampo.qtdCaracteres(vNomeAparelho.getCampoNomeAparelho(), 3);
    }
}
