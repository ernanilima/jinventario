package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.adapter.ItemContagemRecyclerAdapter;
import br.com.ernanilima.jinventario.interfaces.IContagem;
import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.presenter.ContagemPresenter;
import br.com.ernanilima.jinventario.service.componente.SwipeHelper;

public class ContagemFragment extends Fragment implements IContagem.IView {

    public static final String CODIGO_CONTAGEM = "CodigoContagem";

    private IContagem.IPresenter pContagem;
    private TextInputLayout campo_codbarras, campo_qtd_dcaixa, campo_qtd_pcaixa;
    private AppCompatButton btn_ok;
    private RecyclerView recycler_view;
    private ItemContagemRecyclerAdapter raItemContagem;

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
        recycler_view = view.findViewById(R.id.recycler_view);

        // ACAO DE BOTOES
        campo_qtd_pcaixa.getEditText().setOnClickListener(v -> pContagem.adicionarItemColetado()); // botao de teclado
        btn_ok.setOnClickListener(v -> pContagem.adicionarItemColetado());

    }

    @Override
    /** Exibe o titulo com o numero(id) da contagem */
    public void setIdParaExibirNoTitulo(long idContagem) {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        CharSequence tituloAtual = actionBar.getTitle();
        String tituloNovo = tituloAtual + " N˚ " + idContagem;
        actionBar.setTitle(tituloNovo);
    }

    @Override
    /** Regista o Recycler Adapter com a lista de itens do parametro
     * Define o Recycler Adapter no Recycler View do xml de contagem */
    public void setRecyclerAdapter(List<ItemContagem> lsItensContagem) {
        raItemContagem = new ItemContagemRecyclerAdapter(lsItensContagem);
        raItemContagem.setContagemPresenter(pContagem);
        recycler_view.setHasFixedSize(true);
        recycler_view.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayout.VERTICAL));
        recycler_view.setAdapter(raItemContagem);
        SwipeHelper swipeHelper = new SwipeHelper(); // classe de slider no item
        swipeHelper.setRecyclerView(recycler_view);
    }

    @Override
    /** Usado para atualizar o Recycler Adapter apos um item ser adicionado ou alterado */
    public void atualizarRecyclerAdapter() {
        raItemContagem.notifyDataSetChanged();
    }

    @Override
    /** Usado para alterar um item na lista de itens atribuida ao recycler adapter */
    public void setItemAlterado(ItemContagem mItemContagem) {
        raItemContagem.setItemAlterado(mItemContagem);
    }

    @Override
    /** Usado para excluir um item na lista de itens atribuida ao recycler adapter */
    public void setItemExcluido(ItemContagem mItemContagem) {
        raItemContagem.setItemExcluido(mItemContagem);
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
