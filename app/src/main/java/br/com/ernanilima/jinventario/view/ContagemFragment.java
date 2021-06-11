package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.adapter.ContagemEstoqueRecyclerAdapter;
import br.com.ernanilima.jinventario.interfaces.IContagem;
import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.presenter.ContagemPresenter;
import br.com.ernanilima.jinventario.service.component.SwipeHelper;

public class ContagemFragment extends Fragment implements IContagem.IView {

    public static final String CODIGO_CONTAGEM = "CodigoContagem";

    private IContagem.IPresenter pContagem;
    private TextInputLayout campo_codbarras, campo_qtd_dcaixa, campo_qtd_pcaixa;
    private AppCompatButton btn_ok;
    private AppCompatImageButton btn_scanner_camera;
    private RecyclerView recycler_view;
    private ContagemEstoqueRecyclerAdapter raItemContagem;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MenuItem menuItem;

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
        btn_scanner_camera = view.findViewById(R.id.btn_scannercamera);
        btn_ok = view.findViewById(R.id.btn_ok);
        recycler_view = view.findViewById(R.id.recycler_view);

        // ACAO DE BOTOES
        campo_qtd_pcaixa.getEditText().setOnClickListener(v -> pContagem.adicionarItemColetado()); // botao de teclado
        btn_scanner_camera.setOnClickListener(v -> pContagem.abrirCameraScanner());
        btn_ok.setOnClickListener(v -> pContagem.adicionarItemColetado());

    }

    @Override
    public void onStart() {
        super.onStart();
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout); // captura o drawer layout
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view); // captura o navigation view
        menuItem = navigationView.getMenu().findItem(R.id.nav_contagem); // captura o item de menu com base no seu id
        menuItem.setIcon(R.drawable.ic_contagem); // icone no item de menu
        menuItem.setOnMenuItemClickListener(item -> { // ao clicao no botao nav_contagem
            drawerLayout.closeDrawers(); // fecha o drawer layout
            return true;
        });
    }

    @Override
    /** Recebe argumentos no parametro
     * Usado para enviar a interface de resposta da camera scanner */
    public void setArgumentoBundle(Bundle argumentoBundle) {
        getParentFragmentManager().setFragmentResult(CameraScannerFragment.class.getCanonicalName(), argumentoBundle);
    }

    @Override
    /** Exibe o titulo com base no parametro recebido
     * Item de menu no drawer layout e no toolbar */
    public void setIdParaExibirNoTitulo(long idContagem) {
        String tituloNovo = "Contagem N˚ " + idContagem;

        // titulo item de menu no drawer layout
        menuItem.setTitle(tituloNovo);

        // titulo da aplicacao no toolbar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(tituloNovo);
    }

    @Override
    /** Regista o Recycler Adapter com a lista de itens do parametro
     * Define o Recycler Adapter no Recycler View do xml de contagem */
    public void setRecyclerAdapter(List<ItemContagem> lsItensContagem) {
        raItemContagem = new ContagemEstoqueRecyclerAdapter(lsItensContagem);
        raItemContagem.setContagemPresenter(pContagem);
        recycler_view.setHasFixedSize(true);
        recycler_view.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayout.VERTICAL));
        recycler_view.setAdapter(raItemContagem);
        SwipeHelper swipeHelper = new SwipeHelper(); // classe de slider no item
        swipeHelper.setRecyclerView(recycler_view);
    }

    @Override
    /** Usado para atualizar o Recycler Adapter apos o item ser
     * adicionado, alterado ou excluido */
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

    @Override
    /** Cronstroi o menu */
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_menu_contagem, menu);
    }

    @Override
    /** Opcao de menu selecionada */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.compartilhar:
                pContagem.toolbarCompartilhar();
                return true;
        }
        return false;
    }
}
