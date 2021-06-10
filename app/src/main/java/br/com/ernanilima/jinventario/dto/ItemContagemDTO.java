package br.com.ernanilima.jinventario.dto;

import br.com.ernanilima.jinventario.model.ItemContagem;

/** DTO usado para gravar os itens no firebase */
public class ItemContagemDTO {

    private Long idContagem;
    private String codigoBarras;
    private String qtdDeCaixas;
    private String qtdPorCaixa;
    private String qtdTotal;

    public ItemContagemDTO(ItemContagem mItemContagem) {
        this.idContagem = mItemContagem.getIdContagem();
        this.codigoBarras = mItemContagem.getCodigoBarras();
        this.qtdDeCaixas = mItemContagem.getQtdDeCaixas();
        this.qtdPorCaixa = mItemContagem.getQtdPorCaixa();
        this.qtdTotal = mItemContagem.getQtdTotal();
    }

    public Long getIdContagem() {
        return idContagem;
    }
    public String getCodigoBarras() {
        return codigoBarras;
    }
    public String getQtdDeCaixas() {
        return qtdDeCaixas;
    }
    public String getQtdPorCaixa() {
        return qtdPorCaixa;
    }
    public String getQtdTotal() {
        return qtdTotal;
    }
}
