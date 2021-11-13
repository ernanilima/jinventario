package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import java.util.Date;

import br.com.ernanilima.jinventario.BaseApplication;
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth;
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth;
import br.com.ernanilima.jinventario.data.network.firebase.TipoResultado;
import br.com.ernanilima.jinventario.interfaces.ICadastro;
import br.com.ernanilima.jinventario.model.EmailVerificacao;
import br.com.ernanilima.jinventario.repository.orm.DaoSession;
import br.com.ernanilima.jinventario.repository.orm.EmailVerificacaoDao;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.greendao.EmailEnviado;
import br.com.ernanilima.jinventario.service.navcontroller.Navigation;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.service.validation.ValidarInternet;
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado;

public class CadastroPresenter implements ICadastro.IPresenter {

    private ICadastro.IView vCadastro;
    private IFirebaseAuth iFirebaseAuth;
    private DaoSession daoSession;
    private EmailVerificacaoDao dEmailVerificacao;

    /** Construtor
     * @param vCadastro ICadastro.IView - view(fragment) de cadastro */
    public CadastroPresenter(ICadastro.IView vCadastro) {
        this.vCadastro = vCadastro;
        this.iFirebaseAuth = new FirebaseAuth(this);

        // GREENDAO
        this.daoSession = ((BaseApplication) this.vCadastro.getActivity().getApplication()).getSessao();
        this.dEmailVerificacao = daoSession.getEmailVerificacaoDao();
    }

    @Override
    public void cadastrar(View view) {
        if (validarCampos() && validarPoliticaPrivacidade() && validarInternet()) {
            String email = vCadastro.getCampoEmail().getEditText().getText().toString();
            String senha = vCadastro.getCampoSenha1().getEditText().getText().toString();
            iFirebaseAuth.registerUser(view.getContext(), email, senha);
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vCadastro.getCampoEmail(), MensagensAlerta.EMAIL_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(vCadastro.getCampoSenha1(), MensagensAlerta.SENHA_INVALIDA.getMsg()) &&
                ValidarCampo.vazio(vCadastro.getCampoSenha2(), MensagensAlerta.SENHA_INVALIDA.getMsg()) &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha1(), 6) &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha2(), 6) &&
                ValidarCampo.senhasIguais(vCadastro.getCampoSenha1(), vCadastro.getCampoSenha2(), MensagensAlerta.SENHAS_NAO_COMBINAM.getMsg());
    }

    private boolean validarPoliticaPrivacidade() {
        boolean politicaPrivacidade = vCadastro.getChbxPoliticaPrivacidadeSelecionado();
        if (!politicaPrivacidade) {
            ToastPersonalizado.erro(vCadastro.getActivity(), MensagensAlerta.POLITICA_PRIVACIDADE.getMsg());
        }

        return politicaPrivacidade;
    }

    private boolean validarInternet() {
        boolean internet = ValidarInternet.conexao(vCadastro.getActivity());
        if (!internet) {
            ToastPersonalizado.erro(vCadastro.getActivity(), MensagensAlerta.SEM_INTERNET.getMsg());
        }

        return internet;
    }

    /** Grava no banco greendao o e-mail cadastrado e instante do envio do e-mail de verificacao
     * Se ja existir, atualiza */
    private void gravarEmailVerificacaoEnviado() {
        String email = vCadastro.getCampoEmail().getEditText().getText().toString();

        // realiza busca no banco greendao para verificar se cadastro ja existe com base no e-mail
        // se cadastro nao for localizado, a busca retorna um novo model com e-mail do parametro
        EmailVerificacao mEmailVerificacao = EmailEnviado.getInstance().getEmailVerificacao(email, dEmailVerificacao);

        mEmailVerificacao.setDataEnvioVerificacao(new Date(System.currentTimeMillis())); // atribui instante atual para e-mail de verificacao enviado

        dEmailVerificacao.save(mEmailVerificacao); // save e updade eh o mesmo, o que muda eh se existe id no que vai ser gravado
    }

    @Override
    /** Resultado recebido do firebase */
    public void setResultado(TipoResultado resultado) {
        switch (resultado) {
            case CADASTRO_REALIZADO:
                // cadastro realizado, envia o e-mail de verificacao
                // para que o usuario confirme se o e-mail realmente existe
                iFirebaseAuth.sendEmailVerification(vCadastro.getActivity());
                break;

            case EMAIL_VERIFICACAO_ENVIADO:
                gravarEmailVerificacaoEnviado();
                ToastPersonalizado.sucesso(vCadastro.getActivity().getApplicationContext(), MensagensAlerta.USUARIO_CADASTRADO.getMsg());
                Navigation.Login.Companion.toLoginFragment(vCadastro.requireParentFragment());
        }
    }
}
