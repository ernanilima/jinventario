package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public interface ICadastro {

    /** Usado na activity
     * {@link br.com.ernanilima.jinventario.view.CadastroActivity} */
    interface CadastroView {
        Activity getActivity();
        TextInputLayout getCampoEmail();
        TextInputLayout getCampoSenha1();
        TextInputLayout getCampoSenha2();
    }

    /** Usado no presenter
     * {@link br.com.ernanilima.jinventario.presenter.CadastroPresenter} */
    interface CadastroPresenter extends IResultadoFirebase {
        void cadastrar(View view);
    }
}
