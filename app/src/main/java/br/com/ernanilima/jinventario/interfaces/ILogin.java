package br.com.ernanilima.jinventario.interfaces;

import com.google.android.material.textfield.TextInputLayout;

public interface ILogin {

    /** Usado na activity
     * {@link br.com.ernanilima.jinventario.view.LoginActivity} */
    interface LoginView {
        TextInputLayout getCampoEmail();
        TextInputLayout getCampoSenha();
    }

    /** Usado no presenter
     * {@link br.com.ernanilima.jinventario.presenter.LoginPresenter} */
    interface LoginPresenter {
        void login();
        void loginGmail();
    }
}
