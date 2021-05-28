package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.interfaces.ILogin;

public class LoginPresenter implements ILogin.LoginPresenter {

    private ILogin.LoginView vLogin;

    public LoginPresenter(ILogin.LoginView vLogin) {
        this.vLogin = vLogin;
    }

    @Override
    public void login() {
        System.out.println("login normal");
    }

    @Override
    public void loginGmail() {
        System.out.println("login gmail");
    }
}
