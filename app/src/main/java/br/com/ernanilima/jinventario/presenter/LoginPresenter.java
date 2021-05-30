package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;
import br.com.ernanilima.jinventario.interfaces.ILogin;
import br.com.ernanilima.jinventario.service.navcontroller.Navegacao;
import br.com.ernanilima.jinventario.service.social.Google;

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
        Google.getInstance().loginGoogle(vLogin.getServicoLoginGoogle(), this);
    }

    @Override
    public void setResultado(TipoResultado resultado) {
        switch (resultado) {
            case LOGIN_GOOGLE:
                Navegacao.abrirTelaActivityPrincipal(vLogin.getActivity());
        }
    }
}
