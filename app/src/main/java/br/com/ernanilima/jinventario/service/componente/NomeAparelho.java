package br.com.ernanilima.jinventario.service.componente;

import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.NomeAparelhoDao;

public class NomeAparelho {

    private static NomeAparelho NOME_APARELHO;
    private DaoSession daoSession;
    private NomeAparelhoDao dNomeAparelho;

    /** @param daoSession DaoSession - sessao do greendao
     * @return NomeAparelho - instancia da classe {@link NomeAparelho} */
    public static NomeAparelho getInstance(DaoSession daoSession) {
        if (NOME_APARELHO == null) {
            NOME_APARELHO = new NomeAparelho(daoSession);
        } return NOME_APARELHO;
    }

    /** Construtor
     * @param daoSession DaoSession - sessao do greendao */
    public NomeAparelho(DaoSession daoSession) {
        // GREENDAO
        this.daoSession = daoSession;
        this.dNomeAparelho = this.daoSession.getNomeAparelhoDao();
    }

    /** Verifica no banco greendao se o nome ja existe
     * @return boolean - true se nome ja existir */
    public boolean getNomeExiste() {
        // 1L eh o id do nome gravado
        return dNomeAparelho.load(1L) != null;
    }
}
