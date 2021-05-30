package br.com.ernanilima.jinventario.interfaces;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.textfield.TextInputLayout;

public interface ILogin {

    /** Usado na activity
     * {@link br.com.ernanilima.jinventario.view.LoginActivity} */
    interface LoginView {
        Activity getActivity();
        TextInputLayout getCampoEmail();
        TextInputLayout getCampoSenha();
        GoogleSignInClient getServicoLoginGoogle();
    }

    /** Usado no presenter
     * {@link br.com.ernanilima.jinventario.presenter.LoginPresenter} */
    interface LoginPresenter extends IResultadoFirebase {
        void login();
        void loginGmail();
    }
}
