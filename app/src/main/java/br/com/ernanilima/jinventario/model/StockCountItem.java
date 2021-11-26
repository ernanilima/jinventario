package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

import br.com.ernanilima.jinventario.repository.orm.DaoSession;
import br.com.ernanilima.jinventario.repository.orm.StockCountDao;
import br.com.ernanilima.jinventario.repository.orm.StockCountItemDao;

@Entity(nameInDb = "STOCK_COUNT_ITEM")
public class StockCountItem implements Serializable, IModel {
    private static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "STOCK_COUNT")
    private Long stockCount;
    @Property(nameInDb = "BARCODE")
    private String barcode;
    @Property(nameInDb = "UNIT_PRICE")
    private Double unitPrice;
    @Property(nameInDb = "NUMBER_OF_BOXES")
    private String numberOfBoxes;
    @Property(nameInDb = "NUMBER_PER_BOX")
    private String numberPerBox;
    @ToOne(joinProperty = "stockCount")
    private StockCount mStockCount;
    @Transient
    private int index;

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    // tudo abaixo eh construido automaticamente ao executar o projeto

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1730699988)
    private transient StockCountItemDao myDao;

    @Generated(hash = 2002086092)
    public StockCountItem(Long id, Long stockCount, String barcode,
            Double unitPrice, String numberOfBoxes, String numberPerBox) {
        this.id = id;
        this.stockCount = stockCount;
        this.barcode = barcode;
        this.unitPrice = unitPrice;
        this.numberOfBoxes = numberOfBoxes;
        this.numberPerBox = numberPerBox;
    }

    @Generated(hash = 1843648589)
    public StockCountItem() {
    }

    @Generated(hash = 666498259)
    private transient Long mStockCount__resolvedKey;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockCount() {
        return this.stockCount;
    }

    public void setStockCount(Long stockCount) {
        this.stockCount = stockCount;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getNumberOfBoxes() {
        return this.numberOfBoxes;
    }

    public void setNumberOfBoxes(String numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }

    public String getNumberPerBox() {
        return this.numberPerBox;
    }

    public void setNumberPerBox(String numberPerBox) {
        this.numberPerBox = numberPerBox;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2038891101)
    public StockCount getMStockCount() {
        Long __key = this.stockCount;
        if (mStockCount__resolvedKey == null
                || !mStockCount__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StockCountDao targetDao = daoSession.getStockCountDao();
            StockCount mStockCountNew = targetDao.load(__key);
            synchronized (this) {
                mStockCount = mStockCountNew;
                mStockCount__resolvedKey = __key;
            }
        }
        return mStockCount;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 437813034)
    public void setMStockCount(StockCount mStockCount) {
        synchronized (this) {
            this.mStockCount = mStockCount;
            stockCount = mStockCount == null ? null : mStockCount.getId();
            mStockCount__resolvedKey = stockCount;
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
    @Generated(hash = 1765670030)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStockCountItemDao() : null;
    }
}
