package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import java.util.Date;

import br.com.ernanilima.jinventario.BaseApplication;
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth;
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth;
import br.com.ernanilima.jinventario.data.result.IResultType;
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase;
import br.com.ernanilima.jinventario.interfaces.IEsqueceuSenha;
import br.com.ernanilima.jinventario.model.EmailNovaSenha;
import br.com.ernanilima.jinventario.repository.orm.DaoSession;
import br.com.ernanilima.jinventario.repository.orm.EmailNovaSenhaDao;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.greendao.EmailEnviado;
import br.com.ernanilima.jinventario.service.navcontroller.Navigation;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.service.validation.ValidarEmailEnviado;
import br.com.ernanilima.jinventario.service.validation.ValidarInternet;
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado;

public class EsqueceuSenhaPresenter implements IEsqueceuSenha.IPresenter {

    private IEsqueceuSenha.IView vEsqueceuSenha;
    private IFirebaseAuth iFirebaseAuth;
    private DaoSession daoSession;
    private EmailNovaSenhaDao dEmailNovaSenha;

    /** Construtor
     * @param vEsqueceuSenha IEsqueceuSenha.IView - view(fragment) de esqueceu senha */
    public EsqueceuSenhaPresenter(IEsqueceuSenha.IView vEsqueceuSenha) {
        this.vEsqueceuSenha = vEsqueceuSenha;
        iFirebaseAuth = new FirebaseAuth(this);

        // GREENDAO
        this.daoSession = ((BaseApplication) this.vEsqueceuSenha.getActivity().getApplication()).getSessao();
        this.dEmailNovaSenha = daoSession.getEmailNovaSenhaDao();
    }

    @Override
    public void gerarNovaSenha(View view) {
        if (validarCampo() && validarInternet()) {
            // verificacoes para saber se pode enviar um e-mail de nova senha
            enviarEmailNovaSenha();
        }
    }

    /** Verifica se o usuario pode enviar o e-mail de nova senha */
    private void enviarEmailNovaSenha() {
        String email = vEsqueceuSenha.getCampoEmail().getEditText().getText().toString();

        // realiza busca no banco greendao para verificar se cadastro ja existe com base no e-mail
        // se cadastro nao for localizado, a busca retorna um novo model com e-mail do parametro
        EmailNovaSenha mEmailNovaSenha = EmailEnviado.getInstance().getEmailNovaSenha(email, dEmailNovaSenha);

        // se o id for null, significa que a data/hora para o e-mail enviado como parametro nao costa gravado
        // como o cadastro nao existe, envia um e-mail de verificacao e realiza o cadastro do instante do envio realizado.
        // se o id nao for null, verifica se um e-mail pode ser enviado
        if (mEmailNovaSenha.getId() == null || ValidarEmailEnviado.isEnviarNovoEmail(mEmailNovaSenha.getDataEnvioNovaSenha())) {
            iFirebaseAuth.sendEmailForgotPassword(vEsqueceuSenha.getActivity(), email); // envia um e-mail
            mEmailNovaSenha.setDataEnvioNovaSenha(new Date(System.currentTimeMillis())); // atribui instante atual para e-mail enviado
            dEmailNovaSenha.save(mEmailNovaSenha); // save e updade eh o mesmo, o que muda eh se existe id no que vai ser gravado
        }
        // se o e-mail nao puder ser enviado, exibe um toast com o tempo que o usuario deve aguardar para um novo envio
        else {
            ToastPersonalizado.erro(vEsqueceuSenha.getActivity().getApplicationContext(), MensagensAlerta.getMsgTempoEsperaEmail(
                    ValidarEmailEnviado.getTempoParaNovoEmail(mEmailNovaSenha.getDataEnvioNovaSenha())));
        }
    }

    private boolean validarCampo() {
        return ValidarCampo.vazio(vEsqueceuSenha.getCampoEmail(), MensagensAlerta.EMAIL_INVALIDO.getMsg());
    }

    private boolean validarInternet() {
        boolean internet = ValidarInternet.conexao(vEsqueceuSenha.getActivity());
        if (!internet) {
            ToastPersonalizado.erro(vEsqueceuSenha.getActivity(), MensagensAlerta.SEM_INTERNET.getMsg());
        }

        return internet;
    }

    @Override
    /** Resultado recebido do firebase */
    public void setResult(IResultType iResult) {
        if (ResultTypeFirebase.NEW_PASSWORD_EMAIL_SENT.equals(iResult)) {
            ToastPersonalizado.sucesso(vEsqueceuSenha.getActivity().getApplicationContext(), MensagensAlerta.EMAIL_NOVA_SENHA_ENVIADA.getMsg());
            Navigation.Login.Companion.toLoginFragment(vEsqueceuSenha.requireParentFragment());
        }
    }
}
