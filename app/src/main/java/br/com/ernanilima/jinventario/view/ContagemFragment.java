package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.adapter.ItemContagemRecycleAdapter;
import br.com.ernanilima.jinventario.adapter.interfaces.IRecycleAdapter;
import br.com.ernanilima.jinventario.interfaces.IContagem;
import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.presenter.ContagemPresenter;

public class ContagemFragment extends Fragment implements IContagem.IView {

    public static final String CODIGO_CONTAGEM = "CodigoContagem";

    private IContagem.IPresenter pContagem;
    private TextInputLayout campo_codbarras, campo_qtd_dcaixa, campo_qtd_pcaixa;
    private AppCompatButton btn_ok;
    private RecyclerView recycle_view;
    private ItemContagemRecycleAdapter raItemContagem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // argumento recebido de outro fragment, basicamente recebe o codigo de uma nova contagem ou de uma existente
        getParentFragmentManager().setFragmentResultListener(this.getClass().getCanonicalName(), this,
                (requestKey, result) -> pContagem.setCodigoContagem(result.getLong(CODIGO_CONTAGEM)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_contagem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // DEFINE PRESENTER DESSA ACTIVITY
        pContagem = new ContagemPresenter(this);

        //INICIALIZA
        campo_codbarras = view.findViewById(R.id.campo_codbarras);
        campo_qtd_dcaixa = view.findViewById(R.id.campo_qtd_dcaixa);
        campo_qtd_pcaixa = view.findViewById(R.id.campo_qtd_pcaixa);
        btn_ok = view.findViewById(R.id.btn_ok);
        recycle_view = view.findViewById(R.id.recycle_view);

        // ACAO DE BOTOES
        campo_qtd_pcaixa.getEditText().setOnClickListener(v -> pContagem.adicionarItemColetado()); // botao de teclado
        btn_ok.setOnClickListener(v -> pContagem.adicionarItemColetado());

    }

    @Override
    /** Regista o Recycle Adapter com a lista de itens do parametro
     * Define o Recycle Adapter no Recycle View do xml de contagem */
    public void setRecycleAdapter(List<ItemContagem> lsItensContagem) {
        raItemContagem = new ItemContagemRecycleAdapter(lsItensContagem);
        raItemContagem.setIRecycleAdapter((IRecycleAdapter) pContagem);
        recycle_view.setHasFixedSize(true);
        recycle_view.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayout.VERTICAL));
        recycle_view.setAdapter(raItemContagem);
    }

    @Override
    /** Usado para atualizar o Recycle Adapter apos um item ser adicionado ou alterado */
    public void atualizarRecycleView() {
        raItemContagem.notifyDataSetChanged();
    }

    @Override
    public TextInputLayout getCampoCodbarras() {
        return campo_codbarras;
    }

    @Override
    public TextInputLayout getCampoQtdDeCaixa() {
        return campo_qtd_dcaixa;
    }

    @Override
    public TextInputLayout getCampoQtdPorCaixa() {
        return campo_qtd_pcaixa;
    }
}
