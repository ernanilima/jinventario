package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.presenter.LoginPresenter;
import br.com.ernanilima.jinventario.view.LoginFragment;

public interface ILogin {

    /** Usado no fragment
     * {@link LoginFragment} */
    interface View {
        Activity getActivity();
        TextInputLayout getCampoEmail();
        TextInputLayout getCampoSenha();
        GoogleSignInClient getServicoLoginGoogle();
    }

    /** Usado no presenter
     * {@link LoginPresenter} */
    interface Presenter extends IResultadoFirebase {
        void login();
        void loginGmail();
        void verificarSeUsuarioAutenticado();
    }
}
