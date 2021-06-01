package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import java.util.Date;

import br.com.ernanilima.jinventario.DbGreenDao;
import br.com.ernanilima.jinventario.firebase.Firebase;
import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;
import br.com.ernanilima.jinventario.firebase.interfaces.IFirebase;
import br.com.ernanilima.jinventario.interfaces.ICadastro;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.EmailEnviado;
import br.com.ernanilima.jinventario.model.EmailEnviadoDao;
import br.com.ernanilima.jinventario.service.constant.MensagemAlerta;
import br.com.ernanilima.jinventario.service.navcontroller.Navegacao;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado;

public class CadastroPresenter implements ICadastro.CadastroPresenter {

    private ICadastro.CadastroView vCadastro;
    private IFirebase iFirebase;
    private DaoSession daoSession;
    private EmailEnviadoDao dEmailEnviado;

    /** Construtor
     * @param vCadastro ICadastro.CadastroView - view(activity) de cadastro */
    public CadastroPresenter(ICadastro.CadastroView vCadastro) {
        this.vCadastro = vCadastro;
        this.iFirebase = new Firebase(this);

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vCadastro.getActivity().getApplication()).getSessao();
        this.dEmailEnviado = daoSession.getEmailEnviadoDao();
    }

    @Override
    public void cadastrar(View view) {
        if (validarCampos()) {
            String email = vCadastro.getCampoEmail().getEditText().getText().toString();
            String senha = vCadastro.getCampoSenha1().getEditText().getText().toString();
            iFirebase.cadastrarUsuario(view.getContext(), email, senha);
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vCadastro.getCampoEmail(), MensagemAlerta.EMAIL_INVALIDO) &&
                ValidarCampo.vazio(vCadastro.getCampoSenha1(), MensagemAlerta.SENHA_INVALIDA) &&
                ValidarCampo.vazio(vCadastro.getCampoSenha2(), MensagemAlerta.SENHA_INVALIDA) &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha1(), 6) &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha2(), 6) &&
                ValidarCampo.senhasIguais(vCadastro.getCampoSenha1(), vCadastro.getCampoSenha2(), MensagemAlerta.SENHAS_NAO_COMBINAM);
    }

    /** Grava no banco o e-mail cadastrado e momento do envio da verificacao */
    private void emailVerificacaoEnviado() {
        EmailEnviado mEmailEnviado = new EmailEnviado(
                null,
                vCadastro.getCampoEmail().getEditText().getText().toString(),
                new Date(System.currentTimeMillis())
        );

        dEmailEnviado.save(mEmailEnviado);
    }

    @Override
    /** Resultado recebido do firebase */
    public void setResultado(TipoResultado resultado) {
        switch (resultado) {
            case CADASTRO_REALIZADO:
                // cadastro realizado, envia o e-mail de verificacao
                // para confirmar se e-mail realmente existe
                iFirebase.enviarEmailVerificacao(vCadastro.getActivity());
                break;

            case EMAIL_VERIFICACAO_ENVIADO:
                emailVerificacaoEnviado();
                ToastPersonalizado.sucesso(vCadastro.getActivity().getApplicationContext(), "Cadastrado, verifique seu e-mail");
                Navegacao.abrirTelaLogin(vCadastro.getActivity().getCurrentFocus());
        }
    }
}
