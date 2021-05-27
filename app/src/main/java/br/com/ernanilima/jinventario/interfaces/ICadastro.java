package br.com.ernanilima.jinventario.interfaces;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public interface ICadastro {

    /** Usado na activity
     * {@link br.com.ernanilima.jinventario.view.CadastroActivity} */
    interface CadastroView {
        TextInputLayout getCampoEmail();
        TextInputLayout getCampoSenha1();
        TextInputLayout getCampoSenha2();
    }

    /** Usado no presenter
     * {@link br.com.ernanilima.jinventario.presenter.CadastroPresenter} */
    interface CadastroPresenter {
        void cadastrar(View view);
    }
}
