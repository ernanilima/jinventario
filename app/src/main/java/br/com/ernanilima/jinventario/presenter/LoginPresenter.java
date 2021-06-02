package br.com.ernanilima.jinventario.presenter;

import androidx.appcompat.app.AlertDialog;

import java.util.Date;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.firebase.Firebase;
import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;
import br.com.ernanilima.jinventario.firebase.interfaces.IFirebase;
import br.com.ernanilima.jinventario.interfaces.ILogin;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.EmailVerificacao;
import br.com.ernanilima.jinventario.model.EmailVerificacaoDao;
import br.com.ernanilima.jinventario.service.constant.MensagemAlerta;
import br.com.ernanilima.jinventario.service.greendao.EmailEnviado;
import br.com.ernanilima.jinventario.service.navcontroller.Navegacao;
import br.com.ernanilima.jinventario.service.social.Google;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.service.validation.ValidarEmailEnviado;
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado;

public class LoginPresenter implements ILogin.LoginPresenter {

    private ILogin.LoginView vLogin;
    private IFirebase iFirebase;
    private DaoSession daoSession;
    private EmailVerificacaoDao dEmailVerificacao;

    /** Construtor
     * @param vLogin ILogin.LoginView - view(fragment) de login */
    public LoginPresenter(ILogin.LoginView vLogin) {
        this.vLogin = vLogin;
        iFirebase = new Firebase(this);

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vLogin.getActivity().getApplication()).getSessao();
        this.dEmailVerificacao = daoSession.getEmailVerificacaoDao();
    }

    @Override
    /** Antes de exibir a tela, eh verificado se o usuario ja esta autenticado */
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

    /** Exibe o dialog apenas para o usuario que fez o login e nao teve o seu e-mail confirmado */
    private void dialogEmailVerificacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(vLogin.getActivity().getCurrentFocus().getContext());
        builder.setTitle("E-mail não verificado!")
                .setMessage("Reenviar e-mail de verificação?")
                .setPositiveButton("Sim", (dialog, which) -> emailNaoVerificado())
                .setNegativeButton("Não", (dialog, which) -> dialog.cancel())
                .setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /** Verifica se o usuario pode reenviar o e-mail de verificacao */
    private void emailNaoVerificado() {
        String email = vLogin.getCampoEmail().getEditText().getText().toString();

        // realiza busca no banco greendao para verificar se cadastro ja existe com base no e-mail
        // se cadastro nao for localizado, a busca retorna um novo model com e-mail do parametro
        EmailVerificacao mEmailEnviado = EmailEnviado.getInstance().getEmailVerificacao(email, dEmailVerificacao);

        // se o id for null, significa que a data/hora para o e-mail enviado como parametro nao costa gravado
        // como o cadastro nao existe, envia um e-mail de verificacao e realiza o cadastro do instante do envio realizado.
        // se o id nao for null, verifica se um novo e-mail de verificacao pode ser reenviado
        if (mEmailEnviado.getId() == null || ValidarEmailEnviado.isEnviarNovoEmail(mEmailEnviado.getDataEnvioVerificacao())) {
            iFirebase.enviarEmailVerificacao(vLogin.getActivity()); // envia um novo e-mail de verificacao
            mEmailEnviado.setDataEnvioVerificacao(new Date(System.currentTimeMillis())); // atribui instante atual para e-mail de verificacao enviado
            dEmailVerificacao.save(mEmailEnviado); // save e updade eh o mesmo, o que muda eh se existe id no que vai ser gravado
        }
        // se o e-mail nao puder ser enviado, exibe um toast com o tempo que o usuario deve aguardar para um novo envio
        else {
            ToastPersonalizado.erro(vLogin.getActivity().getApplicationContext(),
                    "Aguarde " + ValidarEmailEnviado.getTempoParaNovoEmail(mEmailEnviado.getDataEnvioVerificacao()) + " minuto(s) para enviar o e-mail novamente");
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vLogin.getCampoEmail(), MensagemAlerta.EMAIL_INVALIDO) &&
                ValidarCampo.vazio(vLogin.getCampoSenha(), MensagemAlerta.SENHA_INVALIDA) &&
                ValidarCampo.qtdCaracteres(vLogin.getCampoSenha(), 6);
    }

    @Override
    /** Resultado recebido do firebase */
    public void setResultado(TipoResultado resultado) {
        switch (resultado) {
            case LOGIN_REALIZADO:
                Navegacao.abrirTelaActivityApp(vLogin.getActivity());
                break;
            case EMAIL_NAO_VERIFICADO:
                dialogEmailVerificacao();
        }
    }
}
