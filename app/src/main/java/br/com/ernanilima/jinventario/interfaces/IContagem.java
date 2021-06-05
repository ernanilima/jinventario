package br.com.ernanilima.jinventario.interfaces;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.presenter.ContagemPresenter;
import br.com.ernanilima.jinventario.view.ContagemFragment;
import br.com.ernanilima.jinventario.view.dialog.IResultadoDialog;

public interface IContagem {

    /** Usado no fragment
     * {@link ContagemFragment} */
    interface IView {
        Fragment requireParentFragment();
        void setIdParaExibirNoTitulo(long idContagem);
        void setRecycleAdapter(List<ItemContagem> lsItensContagem);
        void atualizarRecycleAdapter();
        void setItemAlterado(ItemContagem mItemContagem);
        void setItemExcluido(ItemContagem mItemContagem);
        TextInputLayout getCampoCodbarras();
        TextInputLayout getCampoQtdDeCaixa();
        TextInputLayout getCampoQtdPorCaixa();
    }

    /** Usado no presenter
     * {@link ContagemPresenter} */
    interface IPresenter extends IResultadoDialog {
        void setCodigoContagem(Long idContagem);
        void adicionarItemColetado();
        void alterarItemColetado(ItemContagem mItemContagem);
        void excluirItemColetado(ItemContagem mItemContagem);
    }
}
