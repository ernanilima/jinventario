package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

@Entity(nameInDb = "STOCK_COUNT")
public class StockCount implements Serializable, IModel {
    private static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "CREATION_DATE")
    private Date creationDate;
    @Property(nameInDb = "UPDATE_DATE")
    private Date updateDate;
    @Transient
    private long total;
    @Transient
    private int index;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    // tudo abaixo eh construido automaticamente ao executar o projeto

    @Generated(hash = 737933625)
    public StockCount(Long id, Date creationDate, Date updateDate) {
        this.id = id;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    @Generated(hash = 711814895)
    public StockCount() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
