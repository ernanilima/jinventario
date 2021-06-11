package br.com.ernanilima.jinventario.interfaces;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.presenter.ContagemPresenter;
import br.com.ernanilima.jinventario.view.ContagemFragment;

public interface IContagem {

    /** Usado no fragment
     * {@link ContagemFragment} */
    interface IView {
        Fragment requireParentFragment();
        void setArgumentoBundle(Bundle argumentoBundle);
        void setIdParaExibirNoTitulo(long idContagem);
        void setRecyclerAdapter(List<ItemContagem> lsItensContagem);
        void atualizarRecyclerAdapter();
        void setItemAlterado(ItemContagem mItemContagem);
        void setItemExcluido(ItemContagem mItemContagem);
        TextInputLayout getCampoCodbarras();
        TextInputLayout getCampoQtdDeCaixa();
        TextInputLayout getCampoQtdPorCaixa();
    }

    /** Usado no presenter
     * {@link ContagemPresenter} */
    interface IPresenter extends IResultadoDialog, IResultadoCameraScanner {
        void setCodigoContagem(Long idContagem);
        void adicionarItemColetado();
        void abrirCameraScanner();
        void alterarItemColetado(ItemContagem mItemContagem);
        void excluirItemColetado(ItemContagem mItemContagem);
        void toolbarCompartilhar();
    }
}
