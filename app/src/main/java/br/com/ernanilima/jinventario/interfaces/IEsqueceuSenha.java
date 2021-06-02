package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.view.EsqueceuSenhaFragment;

public interface IEsqueceuSenha {

    /** Usado na activity
     * {@link EsqueceuSenhaFragment} */
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
