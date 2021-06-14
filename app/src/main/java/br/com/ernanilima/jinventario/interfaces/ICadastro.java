package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.presenter.CadastroPresenter;
import br.com.ernanilima.jinventario.view.CadastroFragment;

public interface ICadastro {

    /** Usado no fragment
     * {@link CadastroFragment} */
    interface IView {
        Activity getActivity();
        TextInputLayout getCampoEmail();
        TextInputLayout getCampoSenha1();
        TextInputLayout getCampoSenha2();
        boolean getChbxPoliticaPrivacidadeSelecionado();
    }

    /** Usado no presenter
     * {@link CadastroPresenter} */
    interface IPresenter extends IResultadoFirebase {
        void cadastrar(android.view.View view);
    }
}
