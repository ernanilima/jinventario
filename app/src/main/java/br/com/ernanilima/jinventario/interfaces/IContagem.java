package br.com.ernanilima.jinventario.interfaces;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.presenter.ContagemPresenter;
import br.com.ernanilima.jinventario.view.ContagemFragment;

public interface IContagem {

    /** Usado no fragment
     * {@link ContagemFragment} */
    interface IView {
        Fragment requireParentFragment();
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
