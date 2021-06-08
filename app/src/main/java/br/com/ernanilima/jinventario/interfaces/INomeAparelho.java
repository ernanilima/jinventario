package br.com.ernanilima.jinventario.interfaces;

import android.app.Application;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.NomeAparelhoActivity;
import br.com.ernanilima.jinventario.presenter.NomeAparelhoPresenter;

public interface INomeAparelho {

    /** Usado na activity
     * {@link NomeAparelhoActivity} */
    interface IView {
        Application getApplication();
        TextInputLayout getCampoNomeAparelho();
        void finish();
    }

    /** Usado no presenter
     * {@link NomeAparelhoPresenter} */
    interface IPresenter {
        void gravarNomeDoAparelho();
    }
}
