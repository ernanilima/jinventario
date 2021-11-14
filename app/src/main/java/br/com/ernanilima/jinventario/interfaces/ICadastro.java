package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.data.result.IResult;
import br.com.ernanilima.jinventario.presenter.CadastroPresenter;
import br.com.ernanilima.jinventario.view.CadastroFragment;

public interface ICadastro {

    /** Usado no fragment
     * {@link CadastroFragment} */
    interface IView {
        Activity getActivity();
        Fragment requireParentFragment();
        TextInputLayout getCampoEmail();
        TextInputLayout getCampoSenha1();
        TextInputLayout getCampoSenha2();
        boolean getChbxPoliticaPrivacidadeSelecionado();
    }

    /** Usado no presenter
     * {@link CadastroPresenter} */
    interface IPresenter extends IResult {
        void cadastrar(android.view.View view);
    }
}
