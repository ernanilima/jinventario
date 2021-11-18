package br.com.ernanilima.jinventario.presenter;

import android.view.View;

import java.util.Date;

import javax.inject.Inject;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth;
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth;
import br.com.ernanilima.jinventario.data.result.IResultType;
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase;
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom;
import br.com.ernanilima.jinventario.interfaces.ICadastro;
import br.com.ernanilima.jinventario.model.User;
import br.com.ernanilima.jinventario.repository.UserRepository;
import br.com.ernanilima.jinventario.service.navcontroller.Navigation;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.service.validation.ValidarInternet;

public class CadastroPresenter implements ICadastro.IPresenter {

    private ICadastro.IView vCadastro;
    private IFirebaseAuth iFirebaseAuth;
//    private DaoSession daoSession;
//    private EmailVerificacaoDao dEmailVerificacao;
    private UserRepository userDao;

    /** Construtor
     * @param vCadastro ICadastro.IView - view(fragment) de cadastro */
    @Inject
    public CadastroPresenter(ICadastro.IView vCadastro, UserRepository userDao) {
        this.vCadastro = vCadastro;
        this.iFirebaseAuth = new FirebaseAuth(this);
        this.userDao = userDao;
//        // GREENDAO
//        this.daoSession = ((BaseApplication) this.vCadastro.getActivity().getApplication()).getSessao();
//        this.dEmailVerificacao = daoSession.getEmailVerificacaoDao();
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
        return ValidarCampo.vazio(vCadastro.getCampoEmail(), vCadastro.getActivity().getString(R.string.msg_invalid_email)) &&
                ValidarCampo.vazio(vCadastro.getCampoSenha1(), vCadastro.getActivity().getString(R.string.msg_invalid_password)) &&
                ValidarCampo.vazio(vCadastro.getCampoSenha2(), vCadastro.getActivity().getString(R.string.msg_invalid_password)) &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha1(), 6, vCadastro.requireParentFragment().requireContext()) &&
                ValidarCampo.qtdCaracteres(vCadastro.getCampoSenha2(), 6, vCadastro.requireParentFragment().requireContext()) &&
                ValidarCampo.senhasIguais(vCadastro.getCampoSenha1(), vCadastro.getCampoSenha2(), vCadastro.getActivity().getString(R.string.msg_passwords_not_match));
    }

    private boolean validarPoliticaPrivacidade() {
        boolean politicaPrivacidade = vCadastro.getChbxPoliticaPrivacidadeSelecionado();
        if (!politicaPrivacidade) {
            SnackbarCustom.INSTANCE.warning(vCadastro.requireParentFragment().requireContext(), vCadastro.getActivity().getString(R.string.msg_privacy_policy));
        }

        return politicaPrivacidade;
    }

    private boolean validarInternet() {
        boolean internet = ValidarInternet.conexao(vCadastro.getActivity());
        if (!internet) {
            SnackbarCustom.INSTANCE.warning(vCadastro.requireParentFragment().requireContext(), vCadastro.getActivity().getString(R.string.msg_without_internet));
        }

        return internet;
    }

    /** Grava no banco greendao o e-mail cadastrado e instante do envio do e-mail de verificacao
     * Se ja existir, atualiza */
    private void gravarEmailVerificacaoEnviado() {
        String email = vCadastro.getCampoEmail().getEditText().getText().toString();
        User user = userDao.findByEmail(email);
        user.setDateSendingVerification(new Date(System.currentTimeMillis())); // atribui instante atual para e-mail de verificacao enviado
        userDao.insert(user);

//        // realiza busca no banco greendao para verificar se cadastro ja existe com base no e-mail
//        // se cadastro nao for localizado, a busca retorna um novo model com e-mail do parametro
//        EmailVerificacao mEmailVerificacao = EmailEnviado.getInstance().getEmailVerificacao(email, dEmailVerificacao);

//        mEmailVerificacao.setDataEnvioVerificacao(new Date(System.currentTimeMillis())); // atribui instante atual para e-mail de verificacao enviado

//        dEmailVerificacao.save(mEmailVerificacao); // save e updade eh o mesmo, o que muda eh se existe id no que vai ser gravado
    }

    @Override
    /** Resultado recebido do firebase */
    public void setResult(IResultType iResult) {
        if (ResultTypeFirebase.REGISTRATION_DONE.equals(iResult)) {
            // cadastro realizado, envia o e-mail de verificacao
            // para que o usuario confirme se o e-mail realmente existe
            iFirebaseAuth.sendEmailVerification(vCadastro.getActivity());
        } else if (ResultTypeFirebase.VERIFICATION_EMAIL_SENT.equals(iResult)) {
            gravarEmailVerificacaoEnviado();
            SnackbarCustom.INSTANCE.success(vCadastro.requireParentFragment().requireContext(), vCadastro.getActivity().getString(R.string.msg_registered_user));
            Navigation.Login.Companion.toLoginFragment(vCadastro.requireParentFragment());
        }
    }
}
