package br.com.ernanilima.jinventario.presenter;

import androidx.appcompat.app.AlertDialog;

import java.util.Date;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.firebase.FirebaseAutenticacao;
import br.com.ernanilima.jinventario.firebase.TipoResultado;
import br.com.ernanilima.jinventario.firebase.IFirebaseAutenticacao;
import br.com.ernanilima.jinventario.interfaces.ILogin;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.EmailVerificacao;
import br.com.ernanilima.jinventario.model.EmailVerificacaoDao;
import br.com.ernanilima.jinventario.service.component.NomeAparelhoAutenticacao;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.greendao.EmailEnviado;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoNomeAparelho;
import br.com.ernanilima.jinventario.service.social.Google;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.service.validation.ValidarEmailEnviado;
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado;

public class LoginPresenter implements ILogin.IPresenter {

    private ILogin.IView vLogin;
    private IFirebaseAutenticacao iFirebaseAutenticacao;
    private DaoSession daoSession;
    private EmailVerificacaoDao dEmailVerificacao;

    /** Construtor
     * @param vLogin ILogin.IView - view(fragment) de login */
    public LoginPresenter(ILogin.IView vLogin) {
        this.vLogin = vLogin;
        iFirebaseAutenticacao = new FirebaseAutenticacao(this);

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vLogin.getActivity().getApplication()).getSessao();
        this.dEmailVerificacao = daoSession.getEmailVerificacaoDao();
    }

    @Override
    /** Antes de exibir a tela, eh verificado se o usuario ja esta autenticado */
    public void verificarSeUsuarioAutenticado() {
        iFirebaseAutenticacao.verificarSeUsuarioAutenticado();
    }

    @Override
    public void login() {
        if (validarCampos()) {
            String email = vLogin.getCampoEmail().getEditText().getText().toString();
            String senha = vLogin.getCampoSenha().getEditText().getText().toString();
            iFirebaseAutenticacao.loginUsuario(vLogin.getActivity().getBaseContext(), email, senha);
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
                .setPositiveButton("Sim", (dialog, which) -> enviarNovoEmailVerificado())
                .setNegativeButton("Não", (dialog, which) -> dialog.cancel())
                .setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /** Verifica se o usuario pode reenviar o e-mail de verificacao */
    private void enviarNovoEmailVerificado() {
        String email = vLogin.getCampoEmail().getEditText().getText().toString();

        // realiza busca no banco greendao para verificar se cadastro ja existe com base no e-mail
        // se cadastro nao for localizado, a busca retorna um novo model com e-mail do parametro
        EmailVerificacao mEmailEnviado = EmailEnviado.getInstance().getEmailVerificacao(email, dEmailVerificacao);

        // se o id for null, significa que a data/hora para o e-mail enviado como parametro nao costa gravado
        // como o cadastro nao existe, envia um e-mail de verificacao e realiza o cadastro do instante do envio realizado.
        // se o id nao for null, verifica se um e-mail pode ser enviado
        if (mEmailEnviado.getId() == null || ValidarEmailEnviado.isEnviarNovoEmail(mEmailEnviado.getDataEnvioVerificacao())) {
            iFirebaseAutenticacao.enviarEmailVerificacao(vLogin.getActivity()); // envia um novo e-mail de verificacao
            mEmailEnviado.setDataEnvioVerificacao(new Date(System.currentTimeMillis())); // atribui instante atual para e-mail de verificacao enviado
            dEmailVerificacao.save(mEmailEnviado); // save e updade eh o mesmo, o que muda eh se existe id no que vai ser gravado
        }
        // se o e-mail nao puder ser enviado, exibe um toast com o tempo que o usuario deve aguardar para um novo envio
        else {
            ToastPersonalizado.erro(vLogin.getActivity().getApplicationContext(),MensagensAlerta.getMsgTempoEsperaEmail(
                    ValidarEmailEnviado.getTempoParaNovoEmail(mEmailEnviado.getDataEnvioVerificacao())));
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vLogin.getCampoEmail(), MensagensAlerta.EMAIL_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(vLogin.getCampoSenha(), MensagensAlerta.SENHA_INVALIDA.getMsg()) &&
                ValidarCampo.qtdCaracteres(vLogin.getCampoSenha(), 6);
    }

    @Override
    /** Resultado recebido do firebase */
    public void setResultado(TipoResultado resultado) {
        switch (resultado) {
            case LOGIN_REALIZADO:
                // se nome do aparelho ja existir, abre a tela inicial do app com as contagens
                if (NomeAparelhoAutenticacao.getInstance(daoSession).getNomeExiste()) {NavegacaoApp.abrirTelaActivityApp(vLogin.getActivity());}

                // se o nome do aparelho nao existir, abre a tela para criar um nome
                else {NavegacaoNomeAparelho.abrirTelaActivityNomeAparelho(vLogin.getActivity());}
                break;
            case EMAIL_NAO_VERIFICADO:
                dialogEmailVerificacao();
                break;
            case EMAIL_VERIFICACAO_ENVIADO:
                ToastPersonalizado.sucesso(vLogin.getActivity().getApplicationContext(), MensagensAlerta.EMAIL_VERIFICACAO_ENVIADO.getMsg());
        }
    }
}
