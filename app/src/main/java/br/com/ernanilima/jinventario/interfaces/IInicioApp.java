package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;

import br.com.ernanilima.jinventario.view.InicioAppFragment;

public interface IInicioApp {

    /** Usado no fragment
     * {@link InicioAppFragment}
     */
    interface InicioAppView {
        Activity getActivity();
    }

    /** Usado no presenter
     * {@link InicioAppPresenter}
     */
    interface InicioAppPresenter {
        void novaContagem();
    }
}
