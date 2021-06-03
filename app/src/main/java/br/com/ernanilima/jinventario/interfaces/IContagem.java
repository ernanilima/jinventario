package br.com.ernanilima.jinventario.interfaces;

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
        void setRecycleAdapter(List<ItemContagem> lsItensContagem);
        void atualizarRecycleView();
        TextInputLayout getCampoCodbarras();
        TextInputLayout getCampoQtdDeCaixa();
        TextInputLayout getCampoQtdPorCaixa();
    }

    /** Usado no presenter
     * {@link ContagemPresenter} */
    interface IPresenter {
        void setCodigoContagem(Long idContagem);
        void adicionarItemColetado();
    }
}
