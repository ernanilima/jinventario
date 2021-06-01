package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

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

    private void dialogEmailVerificacao(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("E-mail não verificado!")
                .setMessage("Reenviar e-mail de verificação?")
                .setPositiveButton("Sim", (dialog, which) -> iFirebase.enviarEmailVerificacao(vLogin.getActivity()))
                .setNegativeButton("Não", (dialog, which) -> dialog.cancel())
                .setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                Navegacao.abrirTelaActivityApp(vLogin.getActivity());
                break;
            case EMAIL_NAO_VERIFICADO:
                dialogEmailVerificacao(vLogin.getActivity().getCurrentFocus());
        }
    }
}
