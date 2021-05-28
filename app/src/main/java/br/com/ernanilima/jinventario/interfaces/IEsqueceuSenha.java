package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public interface IEsqueceuSenha {

    /** Usado na activity
     * {@link br.com.ernanilima.jinventario.view.EsqueceuSenhaActivity} */
    interface EsqueceuSenhaView {
        Activity getActivity();
        TextInputLayout getCampoEmail();
    }

    /** Usado no presenter
     * {@link br.com.ernanilima.jinventario.presenter.EsqueceuSenhaPresenter} */
    interface EsqueceuSenhaPresenter extends IResultadoFirebase {
        void gerarNovaSenha(View view);
    }
}
