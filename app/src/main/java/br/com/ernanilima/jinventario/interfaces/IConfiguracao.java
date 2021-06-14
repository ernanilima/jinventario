package br.com.ernanilima.jinventario.interfaces;

import br.com.ernanilima.jinventario.presenter.ConfiguracaoPresenter;
import br.com.ernanilima.jinventario.view.ConfiguracaoFragment;

public interface IConfiguracao {

    /** Usado no fragment
     * {@link ConfiguracaoFragment} */
    interface IView {
        boolean getConfigCameraScanner();
    }

    /** Usado no presenter
     * {@link ConfiguracaoPresenter} */
    interface IPresenter {
        void gravarConfiguracao();
    }
}
