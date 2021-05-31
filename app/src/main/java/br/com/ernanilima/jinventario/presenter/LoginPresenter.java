package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.firebase.Firebase;
import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;
import br.com.ernanilima.jinventario.firebase.interfaces.IFirebase;
import br.com.ernanilima.jinventario.interfaces.ILogin;
import br.com.ernanilima.jinventario.service.constant.MensagemAlerta;
import br.com.ernanilima.jinventario.service.navcontroller.Navegacao;
import br.com.ernanilima.jinventario.service.social.Google;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;

public class LoginPresenter implements ILogin.LoginPresenter {

    private ILogin.LoginView vLogin;
    private IFirebase iFirebase;

    public LoginPresenter(ILogin.LoginView vLogin) {
        this.vLogin = vLogin;
        iFirebase = new Firebase(this);
    }

    @Override
    public void verificarSeUsuarioAutenticado() {
        iFirebase.verificarSeUsuarioAutenticado();
    }

    @Override
    public void login() {
        if (validarCampos()) {
            String email = vLogin.getCampoEmail().getEditText().getText().toString();
            String senha = vLogin.getCampoSenha().getEditText().getText().toString();
            iFirebase.loginUsuario(vLogin.getActivity().getBaseContext(), email, senha);
        }
    }

    @Override
    public void loginGmail() {
        Google.getInstance().loginGoogle(vLogin.getServicoLoginGoogle(), this);
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vLogin.getCampoEmail(), MensagemAlerta.EMAIL_INVALIDO) &&
                ValidarCampo.vazio(vLogin.getCampoSenha(), MensagemAlerta.SENHA_INVALIDA) &&
                ValidarCampo.qtdCaracteres(vLogin.getCampoSenha(), 6);
    }

    @Override
    public void setResultado(TipoResultado resultado) {
        switch (resultado) {
            case LOGIN_REALIZADO:
                Navegacao.abrirTelaActivityPrincipal(vLogin.getActivity());
        }
    }
}
