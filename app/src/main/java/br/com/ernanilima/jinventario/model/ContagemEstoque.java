package br.com.ernanilima.jinventario.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

@Entity(nameInDb = "CONTAGEM_ESTOQUE")
public class ContagemEstoque implements Serializable, IModel {
    private static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "DATA_CRIACAO")
    private Date dataCriacao;
    @Property(nameInDb = "DATA_ALTERACAO")
    private Date dataAlteracao;
    @Property(nameInDb = "QTD_TOTAL_ITENS")
    private String qtdTotalItens;
    @Transient // indica que este campo não será gravado no banco de dados.
    private int posicaoItem;

    public int getPosicaoItem() {
        return posicaoItem;
    }
    public void setPosicaoItem(int posicaoItem) {
        this.posicaoItem = posicaoItem;
    }

    // tudo abaixo eh construido automaticamente ao executar o projeto

    @Generated(hash = 20048010)
    public ContagemEstoque() {}

    @Generated(hash = 399844909)
    public ContagemEstoque(Long id, Date dataCriacao, Date dataAlteracao, String qtdTotalItens) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
        this.qtdTotalItens = qtdTotalItens;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getDataCriacao() {
        return this.dataCriacao;
    }
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public Date getDataAlteracao() {
        return this.dataAlteracao;
    }
    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
    public String getQtdTotalItens() {
        return this.qtdTotalItens;
    }
    public void setQtdTotalItens(String qtdTotalItens) {
        this.qtdTotalItens = qtdTotalItens;
    }
}
