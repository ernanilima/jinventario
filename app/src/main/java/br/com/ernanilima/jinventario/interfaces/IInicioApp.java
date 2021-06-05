package br.com.ernanilima.jinventario.interfaces;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.ernanilima.jinventario.model.ContagemEstoque;
import br.com.ernanilima.jinventario.presenter.InicioAppPresenter;
import br.com.ernanilima.jinventario.view.InicioAppFragment;

public interface IInicioApp {

    /** Usado no fragment
     * {@link InicioAppFragment} */
    interface IView {
        Fragment requireParentFragment();
        void setArgumentoBundle(Bundle argumentoBundle);
    }

    /** Usado no presenter
     * {@link InicioAppPresenter} */
    interface IPresenter {
        void novaContagem();
        List<ContagemEstoque> getLsContagensEstoque();
        void alterarContagem(ContagemEstoque mContagemEstoque);
    }
}
