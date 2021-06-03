package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "ITEM_CONTAGEM")
public class ItemContagem {

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "ID_CONTAGEM")
    private Long idContagem;
    @Property(nameInDb = "CODIGO_BARRAS")
    private String codigoBarras;
    @Property(nameInDb = "QTD_DE_CAIXAS")
    private String qtdDeCaixas;
    @Property(nameInDb = "QTD_POR_CAIXA")
    private String qtdPorCaixa;
    @ToOne(joinProperty = "idContagem")
    private ContagemEstoque mContagem;

    // tudo abaixo eh construido automaticamente ao executar o projeto

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 113546829)
    private transient ItemContagemDao myDao;

    @Generated(hash = 434760024)
    public ItemContagem() {}

    @Generated(hash = 353373457)
    public ItemContagem(Long id, Long idContagem, String codigoBarras, String qtdDeCaixas, String qtdPorCaixa) {
        this.id = id;
        this.idContagem = idContagem;
        this.codigoBarras = codigoBarras;
        this.qtdDeCaixas = qtdDeCaixas;
        this.qtdPorCaixa = qtdPorCaixa;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdContagem() {
        return this.idContagem;
    }
    public void setIdContagem(Long idContagem) {
        this.idContagem = idContagem;
    }
    public String getCodigoBarras() {
        return this.codigoBarras;
    }
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
    public String getQtdDeCaixas() {
        return this.qtdDeCaixas;
    }
    public void setQtdDeCaixas(String qtdDeCaixas) {
        this.qtdDeCaixas = qtdDeCaixas;
    }
    public String getQtdPorCaixa() {
        return this.qtdPorCaixa;
    }
    public void setQtdPorCaixa(String qtdPorCaixa) {
        this.qtdPorCaixa = qtdPorCaixa;
    }
    public String getQtdTotal() {
        // CRIADO MANUALMENTE, NAO APAGAR
        return String.valueOf(Long.parseLong(getQtdDeCaixas()) * Long.parseLong(getQtdPorCaixa()));
    }
    @Generated(hash = 54977339)
    private transient Long mContagem__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 744425864)
    public ContagemEstoque getMContagem() {
        Long __key = this.idContagem;
        if (mContagem__resolvedKey == null
                || !mContagem__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContagemEstoqueDao targetDao = daoSession.getContagemEstoqueDao();
            ContagemEstoque mContagemNew = targetDao.load(__key);
            synchronized (this) {
                mContagem = mContagemNew;
                mContagem__resolvedKey = __key;
            }
        }
        return mContagem;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1556523462)
    public void setMContagem(ContagemEstoque mContagem) {
        synchronized (this) {
            this.mContagem = mContagem;
            idContagem = mContagem == null ? null : mContagem.getId();
            mContagem__resolvedKey = idContagem;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 699050161)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getItemContagemDao() : null;
    }

}
