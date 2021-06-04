package br.com.ernanilima.jinventario.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.model.ItemContagem;

public class ItemContagemRecycleAdapter extends RecyclerView.Adapter<ItemContagemRecycleAdapter.ItemViewHolder> {

    private IRecycleAdapter iRecycleAdapter;
    private List<ItemContagem> lsItensContagem;

    /** Construtor
     * @param lsItensContagem List<ItemContagem> - lista de itens */
    public ItemContagemRecycleAdapter(List<ItemContagem> lsItensContagem) {
        this.lsItensContagem = lsItensContagem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inicia o xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contagem_recyclerview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    /** Obtido automaticamente na utilizacao do adapter
     * Usado para adicionar os dados no item que for exibido */
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemContagem mItemContagem = lsItensContagem.get(position);
        holder.campo_codbarras.setText(mItemContagem.getCodigoBarras());
        holder.campo_qtd_dcaixa.setText(mItemContagem.getQtdDeCaixas());
        holder.campo_qtd_pcaixa.setText(mItemContagem.getQtdPorCaixa());
        holder.campo_qtd_total.setText(mItemContagem.getQtdTotal());
        holder.btn_editar.setOnClickListener(v -> {
            mItemContagem.setPosicaoItem(position);
            iRecycleAdapter.alterarItemColetado(mItemContagem);
        });
    }

    /** Usado para que a interface seja atribuida e seu metodo possa ser utilizado
     * @param iRecycleAdapter IRecycleAdapter */
    public void setIRecycleAdapter(IRecycleAdapter iRecycleAdapter) {
        this.iRecycleAdapter = iRecycleAdapter;
    }

    @Override
    /** Obtido automaticamente na utilizacao do adapter
     * Usado para obter o tamanho da lista de itens */
    public int getItemCount() {
        return lsItensContagem.size();
    }

    /** Atualiza a lista de itens com o item alterado que foi recebido no parametro
     * @param mItemContagem ItemContagem - item alterado
     * @return ItemContagem - */
    public void setItemAlterado(ItemContagem mItemContagem) {
        lsItensContagem.set(mItemContagem.getPosicaoItem(), mItemContagem);
    }

    /** Class ViewHolder usada para extender no RecycleAdapter */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView campo_codbarras, campo_qtd_dcaixa, campo_qtd_pcaixa, campo_qtd_total;
        public AppCompatButton btn_editar;

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
