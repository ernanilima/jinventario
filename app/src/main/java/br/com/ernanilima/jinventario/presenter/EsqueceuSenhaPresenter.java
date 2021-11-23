package br.com.ernanilima.jinventario.presenter;

public class EsqueceuSenhaPresenter /*implements IEsqueceuSenha.IPresenter*/ {

//    private IEsqueceuSenha.IView vEsqueceuSenha;
//    private IFirebaseAuth iFirebaseAuth;
////    private DaoSession daoSession;
////    private EmailNovaSenhaDao dEmailNovaSenha;
//    private UserRepository userDao;
//
//    /** Construtor
//     * @param vEsqueceuSenha IEsqueceuSenha.IView - view(fragment) de esqueceu senha */
//    @Inject
//    public EsqueceuSenhaPresenter(IEsqueceuSenha.IView vEsqueceuSenha, UserRepository userDao) {
//        this.vEsqueceuSenha = vEsqueceuSenha;
//        iFirebaseAuth = new FirebaseAuth(this);
//        this.userDao = userDao;
////        // GREENDAO
////        this.daoSession = ((BaseApplication) this.vEsqueceuSenha.getActivity().getApplication()).getSessao();
////        this.dEmailNovaSenha = daoSession.getEmailNovaSenhaDao();
//    }
//
//    @Override
//    public void gerarNovaSenha(View view) {
//        if (validarCampo() && validarInternet()) {
//            // verificacoes para saber se pode enviar um e-mail de nova senha
//            enviarEmailNovaSenha();
//        }
//    }
//
//    /** Verifica se o usuario pode enviar o e-mail de nova senha */
//    private void enviarEmailNovaSenha() {
//        String email = vEsqueceuSenha.getCampoEmail().getEditText().getText().toString();
//        User user = userDao.findByEmail(email);
//
//        if (user.getDateSendingPassword() == null || WaitingTime.INSTANCE.get(user.getDateSendingPassword(), WaitingTime.TEN) <= 0) {
//            iFirebaseAuth.sendEmailForgotPassword(vEsqueceuSenha.getActivity(), email); // envia um e-mail
//            user.setDateSendingPassword(new Date(System.currentTimeMillis())); // atribui instante atual para e-mail enviado
//            userDao.update(user); // save e updade eh o mesmo, o que muda eh se existe id no que vai ser gravado
//        }
//        // se o e-mail nao puder ser enviado, exibe um toast com o tempo que o usuario deve aguardar para um novo envio
//        else {
//            SnackbarCustom.INSTANCE.warning(
//                    vEsqueceuSenha.requireParentFragment().requireContext(),
//                    vEsqueceuSenha.requireParentFragment().getString(
//                            R.string.msg_waiting_time,
//                            String.valueOf(WaitingTime.INSTANCE.get(user.getDateSendingPassword(), WaitingTime.TEN))
//                    )
//            );
//        }
//
////        // realiza busca no banco greendao para verificar se cadastro ja existe com base no e-mail
////        // se cadastro nao for localizado, a busca retorna um novo model com e-mail do parametro
////        EmailNovaSenha mEmailNovaSenha = EmailEnviado.getInstance().getEmailNovaSenha(email, dEmailNovaSenha);
////
////        // se o id for null, significa que a data/hora para o e-mail enviado como parametro nao costa gravado
////        // como o cadastro nao existe, envia um e-mail de verificacao e realiza o cadastro do instante do envio realizado.
////        // se o id nao for null, verifica se um e-mail pode ser enviado
////        if (mEmailNovaSenha.getId() == null || ValidarEmailEnviado.isEnviarNovoEmail(mEmailNovaSenha.getDataEnvioNovaSenha())) {
////            iFirebaseAuth.sendEmailForgotPassword(vEsqueceuSenha.getActivity(), email); // envia um e-mail
////            mEmailNovaSenha.setDataEnvioNovaSenha(new Date(System.currentTimeMillis())); // atribui instante atual para e-mail enviado
////            dEmailNovaSenha.save(mEmailNovaSenha); // save e updade eh o mesmo, o que muda eh se existe id no que vai ser gravado
////        }
////        // se o e-mail nao puder ser enviado, exibe um toast com o tempo que o usuario deve aguardar para um novo envio
////        else {
////            ToastPersonalizado.erro(vEsqueceuSenha.getActivity().getApplicationContext(), MensagensAlerta.getMsgTempoEsperaEmail(
////                    ValidarEmailEnviado.getTempoParaNovoEmail(mEmailNovaSenha.getDataEnvioNovaSenha())));
////        }
//    }
//
//    private boolean validarCampo() {
//        return ValidarCampo.vazio(vEsqueceuSenha.getCampoEmail(), vEsqueceuSenha.requireParentFragment().getString(R.string.msg_invalid_email));
//    }
//
//    private boolean validarInternet() {
//        boolean internet = ValidarInternet.conexao(vEsqueceuSenha.getActivity());
//        if (!internet) {
//            SnackbarCustom.INSTANCE.warning(vEsqueceuSenha.requireParentFragment().requireContext(), vEsqueceuSenha.requireParentFragment().getString(R.string.msg_without_internet));
//        }
//
//        return internet;
//    }
//
//    @Override
//    /** Resultado recebido do firebase */
//    public void setResult(IResultType iResult) {
//        if (ResultTypeFirebase.NEW_PASSWORD_EMAIL_SENT.equals(iResult)) {
//            SnackbarCustom.INSTANCE.success(vEsqueceuSenha.requireParentFragment().requireContext(), vEsqueceuSenha.requireParentFragment().getString(R.string.msg_email_update_password_sent));
//            Navigation.Login.Companion.toLoginFragment(vEsqueceuSenha.requireParentFragment());
//        }
//    }
}
