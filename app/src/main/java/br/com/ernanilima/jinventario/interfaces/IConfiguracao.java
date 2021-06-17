package br.com.ernanilima.jinventario.interfaces;

import androidx.fragment.app.Fragment;

import br.com.ernanilima.jinventario.presenter.ConfiguracaoPresenter;
import br.com.ernanilima.jinventario.view.ConfiguracaoFragment;

public interface IConfiguracao {

    /** Usado no fragment
     * {@link ConfiguracaoFragment} */
    interface IView {
        Fragment requireParentFragment();
        boolean getConfigCameraScanner();
        void setConfigCameraScanner(boolean b);
        boolean getConfigUsarCameraMlkit();
        void setConfigUsarCameraMlkit(boolean b);
        boolean getConfigUsarCameraZxing();
        void setConfigUsarCameraZxing(boolean b);
    }

    /** Usado no presenter
     * {@link ConfiguracaoPresenter} */
    interface IPresenter {
        void gravarConfiguracao();
        void popularDadosConfiguracao();
    }
}
