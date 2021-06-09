package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.adapter.ContagensEstoqueRecyclerAdapter;
import br.com.ernanilima.jinventario.interfaces.IInicioApp;
import br.com.ernanilima.jinventario.model.ContagemEstoque;
import br.com.ernanilima.jinventario.presenter.InicioAppPresenter;
import br.com.ernanilima.jinventario.service.componente.SwipeHelper;

public class InicioAppFragment extends Fragment implements IInicioApp.IView {

    private IInicioApp.IPresenter pInicioApp;
    private AppCompatButton btn_novacontagem;
    private RecyclerView recycler_view;
    private ContagensEstoqueRecyclerAdapter raContagensEstoque;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MenuItem menuItem;
    private AppCompatTextView campo_nome_aparelho, campo_email_usuario;
    private String nomeAparelho, emailUsuario;

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
        raContagensEstoque = new ContagensEstoqueRecyclerAdapter(pInicioApp.getLsContagensEstoque());
        raContagensEstoque.setInicioAppPresenter(pInicioApp);
        recycler_view.setHasFixedSize(true);
        recycler_view.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayout.VERTICAL));
        recycler_view.setAdapter(raContagensEstoque);
        SwipeHelper swipeHelper = new SwipeHelper(); // classe de slider no item
        swipeHelper.setRecyclerView(recycler_view);

    }

    @Override
    public void onStart() {
        super.onStart();
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout); // captura o drawer layout
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view); // captura o navigation view

        // header do drawer layout
        View view = navigationView.getHeaderView(0);
        campo_nome_aparelho = view.findViewById(R.id.campo_nav_nomeaparelho);
        campo_email_usuario = view.findViewById(R.id.campo_nav_email);
        campo_nome_aparelho.setText(nomeAparelho);
        campo_email_usuario.setText(emailUsuario);

        // itens de menu do drawer layout
        menuItem = navigationView.getMenu().findItem(R.id.nav_contagem); // captura o item de menu com base no seu id
        menuItem.setIcon(R.drawable.ic_novo); // icone no item de menu
        menuItem.setTitle(btn_novacontagem.getText()); // atribui no item de menu o mesmo texto do botao

        // acao do clicar
        menuItem.setOnMenuItemClickListener(item -> { // ao clicao no botao nav_contagem
            drawerLayout.closeDrawers(); // fecha o drawer layout
            pInicioApp.novaContagem(); // abre o dialog para criar nova contagem ou nao
            return true;
        });
    }

    @Override
    /** Recebe argumentos no parametro
     * Usado principalmente para receber o codigo de uma
     * nova contagem ou de uma contagem ja existente */
    public void setArgumentoBundle(Bundle argumentoBundle) {
        getParentFragmentManager().setFragmentResult(ContagemFragment.class.getCanonicalName(), argumentoBundle);
    }

    @Override
    public void setContagemExcluida(ContagemEstoque mContagemExcluida) {
        raContagensEstoque.setContagemExcluida(mContagemExcluida);
    }

    @Override
    /** Usado para atualizar o Recycler Adapter apos a contagem de estoque
     * ser adicionado, alterado ou excluida */
    public void atualizarRecyclerAdapter() {
        raContagensEstoque.notifyDataSetChanged();
    }

    @Override
    public void setNomeAparelho(String nomeAparelho) {
        this.nomeAparelho = nomeAparelho;
    }

    @Override
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
