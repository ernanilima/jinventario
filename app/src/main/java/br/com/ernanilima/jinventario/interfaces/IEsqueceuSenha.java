package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.presenter.EsqueceuSenhaPresenter;
import br.com.ernanilima.jinventario.view.EsqueceuSenhaFragment;

public interface IEsqueceuSenha {

    /** Usado no fragment
     * {@link EsqueceuSenhaFragment} */
    interface View {
        Activity getActivity();
        TextInputLayout getCampoEmail();
    }

    /** Usado no presenter
     * {@link EsqueceuSenhaPresenter} */
    interface Presenter extends IResultadoFirebase {
        void gerarNovaSenha(android.view.View view);
    }
}
