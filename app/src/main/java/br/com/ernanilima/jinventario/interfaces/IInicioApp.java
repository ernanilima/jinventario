package br.com.ernanilima.jinventario.interfaces;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import br.com.ernanilima.jinventario.presenter.InicioAppPresenter;
import br.com.ernanilima.jinventario.view.InicioAppFragment;

public interface IInicioApp {

    /** Usado no fragment
     * {@link InicioAppFragment} */
    interface View {
        Fragment requireParentFragment();
        void setArgumentoBundle(Bundle argumentoBundle);
    }

    /** Usado no presenter
     * {@link InicioAppPresenter} */
    interface Presenter {
        void novaContagem();
    }
}
