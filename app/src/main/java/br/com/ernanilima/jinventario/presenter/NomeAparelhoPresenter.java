package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.interfaces.INomeAparelho;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.NomeAparelhoDao;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
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
            System.out.println("GRAVAR NOME DO APARELHO");
        }
    }

    private boolean validarCampo() {
        return ValidarCampo.vazio(vNomeAparelho.getCampoNomeAparelho(), MensagensAlerta.NOME_APARELHO_INVALIDO.getMsg());
    }
}
