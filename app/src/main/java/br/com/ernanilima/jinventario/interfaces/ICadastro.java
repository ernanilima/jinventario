package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.presenter.CadastroPresenter;
import br.com.ernanilima.jinventario.view.CadastroFragment;

public interface ICadastro {

    /** Usado no fragment
     * {@link CadastroFragment} */
    interface View {
        Activity getActivity();
        TextInputLayout getCampoEmail();
        TextInputLayout getCampoSenha1();
        TextInputLayout getCampoSenha2();
    }

    /** Usado no presenter
     * {@link CadastroPresenter} */
    interface Presenter extends IResultadoFirebase {
        void cadastrar(android.view.View view);
    }
}
