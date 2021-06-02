package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

import br.com.ernanilima.jinventario.DbGreenDao;
import br.com.ernanilima.jinventario.firebase.Firebase;
import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;
import br.com.ernanilima.jinventario.firebase.interfaces.IFirebase;
import br.com.ernanilima.jinventario.interfaces.ILogin;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.EmailEnviado;
import br.com.ernanilima.jinventario.model.EmailEnviadoDao;
import br.com.ernanilima.jinventario.service.constant.MensagemAlerta;
import br.com.ernanilima.jinventario.service.navcontroller.Navegacao;
import br.com.ernanilima.jinventario.service.social.Google;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.utils.EnvioEmail;
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado;

public class LoginPresenter implements ILogin.LoginPresenter {

    private ILogin.LoginView vLogin;
    private IFirebase iFirebase;
    private DaoSession daoSession;
    private EmailEnviadoDao dEmailEnviado;

    public LoginPresenter(ILogin.LoginView vLogin) {
        this.vLogin = vLogin;
        iFirebase = new Firebase(this);

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vLogin.getActivity().getApplication()).getSessao();
        this.dEmailEnviado = daoSession.getEmailEnviadoDao();
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
                .setPositiveButton("Sim", (dialog, which) -> emailNaoVerificado())
                .setNegativeButton("Não", (dialog, which) -> dialog.cancel())
                .setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /** Verifica se o usuario pode enviar o e-mail de verificacao novamente */
    private void emailNaoVerificado() {
        // e-mail informado no login
        String email = vLogin.getCampoEmail().getEditText().getText().toString();

        // obtem o model gravado no banco com base no seu e-mail
        EmailEnviado mEmailEnviado = dEmailEnviado.queryBuilder().where(EmailEnviadoDao.Properties.Email.eq(email)).unique();

        // se o model obtido do banco for null, significa que a data/hora do e-mail enviado nao costa gravado
        // se o model obtido do banco nao for null, verifica se um novo e-mail pode ser enviado
        if (mEmailEnviado == null || EnvioEmail.isEnviarNovoEmail(mEmailEnviado.getDataEnvioVerificacao())) {
            iFirebase.enviarEmailVerificacao(vLogin.getActivity());
        }
        // se o e-mail nao puder ser enviado, exibe um toast com o tempo que o usuario deve aguardar para um novo envio
        else {
            ToastPersonalizado.erro(vLogin.getActivity().getApplicationContext(),
                    "Aguarde " + EnvioEmail.getTempoParaNovoEmail(mEmailEnviado.getDataEnvioVerificacao()) + " minuto(s) para enviar o e-mail novamente");
        }
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
