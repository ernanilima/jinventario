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

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.adapter.ContagensEstoqueRecyclerAdapter;
import br.com.ernanilima.jinventario.interfaces.IInicioApp;
import br.com.ernanilima.jinventario.presenter.InicioAppPresenter;

public class InicioAppFragment extends Fragment implements IInicioApp.IView {

    private IInicioApp.IPresenter pInicioApp;
    private AppCompatButton btn_novacontagem;
    private RecyclerView recycler_view;
    private ContagensEstoqueRecyclerAdapter raContagens;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_inicio_app, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // DEFINE PRESENTER DESSA ACTIVITY
        pInicioApp = new InicioAppPresenter(this);

        //INICIALIZA
        // nome_local = nome_no_xml
        btn_novacontagem = view.findViewById(R.id.btn_novacontagem);
        recycler_view = view.findViewById(R.id.recycler_view);

        // ACAO DE BOTOES
        btn_novacontagem.setOnClickListener(v -> pInicioApp.novaContagem());

        //SETs
        raContagens = new ContagensEstoqueRecyclerAdapter(pInicioApp.getLsContagensEstoque());
        recycler_view.setHasFixedSize(true);
        recycler_view.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayout.VERTICAL));
        recycler_view.setAdapter(raContagens);

    }

    @Override
    /** Recebe argumentos no parametro
     * Usado principalmente para receber o codigo de uma
     * nova contagem ou de uma contagem ja existente */
    public void setArgumentoBundle(Bundle argumentoBundle) {
        getParentFragmentManager().setFragmentResult(ContagemFragment.class.getCanonicalName(), argumentoBundle);
    }
}
