package br.com.ernanilima.jinventario.service.componente;

import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.NomeAparelho;
import br.com.ernanilima.jinventario.model.NomeAparelhoDao;

public class NomeAparelhoAutenticacao {

    private static NomeAparelhoAutenticacao NOME_APARELHO;
    private final long codigoNomeAparelho = 1L;
    private NomeAparelho mNomeAparelho;
    private DaoSession daoSession;
    private NomeAparelhoDao dNomeAparelho;

    /** @param daoSession DaoSession - sessao do greendao
     * @return NomeAparelhoAutenticacao - instancia da classe {@link NomeAparelhoAutenticacao} */
    public static NomeAparelhoAutenticacao getInstance(DaoSession daoSession) {
        if (NOME_APARELHO == null) {
            NOME_APARELHO = new NomeAparelhoAutenticacao(daoSession);
        } return NOME_APARELHO;
    }

    /** Construtor
     * @param daoSession DaoSession - sessao do greendao */
    public NomeAparelhoAutenticacao(DaoSession daoSession) {
        // GREENDAO
        this.daoSession = daoSession;
        this.dNomeAparelho = this.daoSession.getNomeAparelhoDao();
    }

    /** Verifica no banco greendao se o nome ja existe
     * @return boolean - true se nome ja existir */
    public boolean getNomeExiste() {
        mNomeAparelho = dNomeAparelho.load(codigoNomeAparelho);
        return mNomeAparelho != null;
    }

    /** @return String - nome do aparelho */
    public String getNomeAparelho() {
        mNomeAparelho = dNomeAparelho.load(codigoNomeAparelho);
        if (mNomeAparelho != null) {
            return mNomeAparelho.getNome();
        }

        return "NOME NÃO CRIADO";
    }
}
