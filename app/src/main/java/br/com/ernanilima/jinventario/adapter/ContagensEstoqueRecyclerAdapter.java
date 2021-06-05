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
import br.com.ernanilima.jinventario.model.ContagemEstoque;
import br.com.ernanilima.jinventario.util.Formatar;

/** Recycler Adapter de contagens de estoque */
public class ContagensEstoqueRecyclerAdapter extends RecyclerView.Adapter<ContagensEstoqueRecyclerAdapter.ContagensViewHolder> {

    private List<ContagemEstoque> lsContagensEstoque;

    /** Construtor
     * @param lsContagensEstoque List<ContagemEstoque> - lista de contagens */
    public ContagensEstoqueRecyclerAdapter(List<ContagemEstoque> lsContagensEstoque) {
        this.lsContagensEstoque = lsContagensEstoque;
    }

    @NonNull
    @Override
    public ContagensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inicia o xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_contagens_recyclerview, parent, false);
        return new ContagensEstoqueRecyclerAdapter.ContagensViewHolder(view);
    }

    @Override
    /** Obtido automaticamente na utilizacao do adapter
     * Usado para adicionar os dados no item exibido no recycler view */
    public void onBindViewHolder(@NonNull ContagensViewHolder holder, int position) {
        ContagemEstoque mContagemEstoque = lsContagensEstoque.get(position);
        holder.campo_numero_contagem.setText(String.valueOf(mContagemEstoque.getId()));
        holder.campo_data_criacao.setText(Formatar.DATA.format(mContagemEstoque.getDataCriacao()));
        holder.campo_data_hora_alteracao.setText(Formatar.DATA_HORA.format(mContagemEstoque.getDataAlteracao()));
        holder.campo_total_itens.setText(String.format("%.0f", Double.parseDouble(mContagemEstoque.getQtdTotalItens())));
    }

    @Override
    /** Obtido automaticamente na utilizacao do adapter
     * Usado para obter o tamanho da lista de itens */
    public int getItemCount() {
        return lsContagensEstoque.size();
    }

    /** Class ViewHolder usada para extender na classe {@link ContagensEstoqueRecyclerAdapter} */
    static class ContagensViewHolder extends RecyclerView.ViewHolder {

        public TextView campo_numero_contagem, campo_data_criacao, campo_data_hora_alteracao, campo_total_itens;
        public AppCompatButton btn_editar;

        public ContagensViewHolder(@NonNull View itemView) {
            super(itemView);

            //INICIALIZA
            campo_numero_contagem = itemView.findViewById(R.id.campo_numero_contagem);
            campo_data_criacao = itemView.findViewById(R.id.campo_data_criacao);
            campo_data_hora_alteracao = itemView.findViewById(R.id.campo_data_hora_alteracao);
            campo_total_itens = itemView.findViewById(R.id.campo_total_itens);
            btn_editar = itemView.findViewById(R.id.btn_editar);
        }
    }
}
