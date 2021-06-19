package br.com.ernanilima.jinventario.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IContagem;
import br.com.ernanilima.jinventario.model.ItemContagem;

/** Recycler Adapter de contagem de estoque */
public class ContagemEstoqueRecyclerAdapter extends RecyclerView.Adapter<ContagemEstoqueRecyclerAdapter.ItemViewHolder> implements IRecyclerAdapter {

    private IContagem.IPresenter pContagem;
    private List<ItemContagem> lsItensContagem;

    /** Construtor
     * @param lsItensContagem List<ItemContagem> - lista de itens */
    public ContagemEstoqueRecyclerAdapter(List<ItemContagem> lsItensContagem) {
        this.lsItensContagem = lsItensContagem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inicia o xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_contagem_recyclerview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    /** Obtido automaticamente na utilizacao do adapter
     * Usado para adicionar os dados no item exibido no recycler view */
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemContagem mItemContagem = lsItensContagem.get(position);
        holder.campo_codbarras.setText(mItemContagem.getCodigoBarras());
        holder.campo_qtd_dcaixa.setText(mItemContagem.getQtdDeCaixas());
        holder.campo_qtd_pcaixa.setText(mItemContagem.getQtdPorCaixa());
        holder.campo_qtd_total.setText(mItemContagem.getQtdTotal());
        holder.btn_editar.setOnClickListener(v -> {
            mItemContagem.setPosicaoItem(position);
            pContagem.alterarItemColetado(mItemContagem);
        });
    }

    /** Usado para que a interface seja atribuida e seus metodos possam ser utilizados
     * @param pContagem IContagem.IPresenter */
    public void setContagemPresenter(IContagem.IPresenter pContagem) {
        this.pContagem = pContagem;
    }

    @Override
    /** Obtido automaticamente na utilizacao do adapter
     * Usado para obter o tamanho da lista de itens */
    public int getItemCount() {
        return lsItensContagem.size();
    }

    /** Atualiza a lista de itens com o item alterado que foi recebido no parametro
     * @param mItemContagem ItemContagem - item alterado */
    public void setItemAlterado(ItemContagem mItemContagem) {
        lsItensContagem.set(mItemContagem.getPosicaoItem(), mItemContagem);
    }

    /** Atualiza a lista de itens com o item excluido que foi recebido no parametro
     * @param mItemContagem ItemContagem - item excluido */
    public void setItemExcluido(ItemContagem mItemContagem) {
        lsItensContagem.remove(mItemContagem.getPosicaoItem());
    }

    @Override
    /** Recebe a posicao do item que deseja excluir
     * Busca o model com base na posicao recebida
     * Envia o model para que sua exclusao possar ser comfirmada pelo usuario */
    public void setPosicaoParaExcluir(int posicaoDoItem) {
        ItemContagem mItemContagem = lsItensContagem.get(posicaoDoItem);
        mItemContagem.setPosicaoItem(posicaoDoItem);
        pContagem.excluirItemColetado(mItemContagem);
    }

    /** Class ViewHolder usada para extender na classe {@link ContagemEstoqueRecyclerAdapter} */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView campo_codbarras, campo_qtd_dcaixa, campo_qtd_pcaixa, campo_qtd_total;
        public ImageView btn_editar;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            //INICIALIZA
            campo_codbarras = itemView.findViewById(R.id.campo_codbarras);
            campo_qtd_dcaixa = itemView.findViewById(R.id.campo_qtd_dcaixa);
            campo_qtd_pcaixa = itemView.findViewById(R.id.campo_qtd_pcaixa);
            campo_qtd_total = itemView.findViewById(R.id.campo_qtd_total);
            btn_editar = itemView.findViewById(R.id.btn_editar);
        }
    }
}
