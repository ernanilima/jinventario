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
import br.com.ernanilima.jinventario.model.ContagemEstoque;
import br.com.ernanilima.jinventario.model.StockCount;
import br.com.ernanilima.jinventario.ui.home.IHome;
import br.com.ernanilima.jinventario.util.Formatar;

/** Recycler Adapter de contagens de estoque */
public class ContagensEstoqueRecyclerAdapter extends RecyclerView.Adapter<ContagensEstoqueRecyclerAdapter.ContagensViewHolder> implements IRecyclerAdapter {

    private IHome.IViewModel pInicioApp;
    private List<StockCount> lsContagensEstoque;

    /** Construtor
     * @param lsContagensEstoque List<ContagemEstoque> - lista de contagens */
    public ContagensEstoqueRecyclerAdapter(List<StockCount> lsContagensEstoque) {
        this.lsContagensEstoque = lsContagensEstoque;
    }

    @NonNull
    @Override
    public ContagensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inicia o xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counts, parent, false);
        return new ContagensEstoqueRecyclerAdapter.ContagensViewHolder(view);
    }

    @Override
    /** Obtido automaticamente na utilizacao do adapter
     * Usado para adicionar os dados no item exibido no recycler view */
    public void onBindViewHolder(@NonNull ContagensViewHolder holder, int position) {
        StockCount mContagemEstoque = lsContagensEstoque.get(position);
        holder.campo_numero_contagem.setText(String.valueOf(mContagemEstoque.getId()));
        holder.campo_data_criacao.setText(Formatar.DATA.format(mContagemEstoque.getCreationDate()));
        holder.campo_data_hora_alteracao.setText(Formatar.DATA_HORA.format(mContagemEstoque.getUpdateDate()));
        holder.campo_total_itens.setText(String.format("%.0f", mContagemEstoque.getTotal()));
        holder.btn_editar.setOnClickListener(v -> {
            pInicioApp.updateCount(mContagemEstoque);
        });
    }

    /** Usado para que a interface seja atribuida e seus metodos possam ser utilizados
     * @param pInicioApp IInicioApp.IPresenter */
    public void setInicioAppPresenter(IHome.IViewModel pInicioApp) {
        this.pInicioApp = pInicioApp;
    }

    @Override
    /** Obtido automaticamente na utilizacao do adapter
     * Usado para obter o tamanho da lista de itens */
    public int getItemCount() {
        return lsContagensEstoque.size();
    }

    @Override
    /** Recebe a posicao do item que deseja excluir
     * Busca o model com base na posicao recebida
     * Envia o model para que sua exclusao possar ser comfirmada pelo usuario */
    public void setPosicaoParaExcluir(int posicaoDoItem) {
        StockCount mContagemEstoque = lsContagensEstoque.get(posicaoDoItem);
        mContagemEstoque.setIndex(posicaoDoItem);
        pInicioApp.deleteCount(mContagemEstoque);
    }

    /** Atualiza a lista de contagens com o contagem excluida que foi recebido no parametro
     * @param mContagemEstoque ContagemEstoque - contagem excluida */
    public void setContagemExcluida(ContagemEstoque mContagemEstoque) {
        lsContagensEstoque.remove(mContagemEstoque.getIndex());
    }

    /** Class ViewHolder usada para extender na classe {@link ContagensEstoqueRecyclerAdapter} */
    static class ContagensViewHolder extends RecyclerView.ViewHolder {

        public TextView campo_numero_contagem, campo_data_criacao, campo_data_hora_alteracao, campo_total_itens;
        public ImageView btn_editar;

        public ContagensViewHolder(@NonNull View itemView) {
            super(itemView);

            //INICIALIZA
            campo_numero_contagem = itemView.findViewById(R.id.field_count_number);
            campo_data_criacao = itemView.findViewById(R.id.field_creation_date);
            campo_data_hora_alteracao = itemView.findViewById(R.id.field_date_hour_update);
            campo_total_itens = itemView.findViewById(R.id.field_total);
            btn_editar = itemView.findViewById(R.id.btn_edit);
        }
    }
}
