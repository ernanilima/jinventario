package br.com.ernanilima.jinventario.presenter;

public class InicioAppPresenter /*implements IInicioApp.IPresenter*/ {

//    private IInicioApp.IView vInicioApp;
//    private IFirebaseAuth iFirebaseAuth;
//    private DaoSession daoSession;
//    private ContagemEstoqueDao dContagemEstoque;
//    private UserRepository userDao;
//
//    /** Construtor
//     * @param vInicioApp IInicioApp.IView - view(fragment) do inicio do app */
//    @Inject
//    public InicioAppPresenter(IInicioApp.IView vInicioApp, DaoSession daoSession, UserRepository userDao) {
//        this.vInicioApp = vInicioApp;
//        iFirebaseAuth = new FirebaseAuth();
//        this.userDao = userDao;
//
//        // GREENDAO
////        this.daoSession = ((BaseApplication) this.vInicioApp.requireParentFragment().getActivity().getApplication()).getSessao();
//        this.daoSession = daoSession;
//        this.dContagemEstoque = daoSession.getContagemEstoqueDao();
//
//        // envia o conteudo para utilizacao no header do drawer layout
////        vInicioApp.setNomeAparelho(NomeAparelhoAutenticacao.getInstance(daoSession).getNomeAparelho());
////        vInicioApp.setEmailUsuario(iFirebaseAuth.getUserEmail());
//        User user = userDao.findByEmail(iFirebaseAuth.getUserEmail());
//        vInicioApp.setNomeAparelho(user.getDeviceName());
//        vInicioApp.setEmailUsuario(user.getEmail());
//    }
//
//    @Override
//    public void novaContagem() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(vInicioApp.requireParentFragment().getContext());
//        builder.setTitle("Contagem de Estoque")
//                .setMessage("Deseja criar uma contagem de estoque?")
//                .setPositiveButton("Sim", (dialog, which) -> criarNovaContagem())
//                .setNegativeButton("Não", (dialog, which) -> dialog.cancel())
//                .setCancelable(false);
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//
//    @Override
//    /** Busca a lista de contagens buscando no banco greendao
//     * @return List<ItemContagem> - lista de itens de contagens */
//    public List<ContagemEstoque> getLsContagensEstoque() {
//        return dContagemEstoque.queryBuilder().orderDesc(ContagemEstoqueDao.Properties.DataAlteracao).list();
//    }
//
//    @Override
//    public void alterarContagem(ContagemEstoque mContagemEstoque) {
//        Bundle argumento = new Bundle();
//        argumento.putLong(ContagemFragment.CODIGO_CONTAGEM, mContagemEstoque.getId());
//        vInicioApp.setArgumentoBundle(argumento);
//
//        NavegacaoApp.abrirTelaContagem(vInicioApp.requireParentFragment().getView());
//    }
//
//    @Override
//    public void excluirContagemEstoque(ContagemEstoque mContagemEstoque) {
//        Bundle argumento = new Bundle();
//        // armazena o model como argumento para que possa ser receptado pelo dialog de exclusao
//        argumento.putSerializable(ExclusaoDialogFragment.MODEL_ITEM_CONTAGEM, mContagemEstoque);
//
//        ExclusaoDialogFragment.novoDialog(this, argumento)
//                .show(vInicioApp.requireParentFragment().getParentFragmentManager());
//    }
//
//    private void criarNovaContagem() {
//        Date dataAtual = new Date(System.currentTimeMillis());
//        ContagemEstoque mContagemEstoque = new ContagemEstoque(null, dataAtual, dataAtual, "0");
//
//        dContagemEstoque.save(mContagemEstoque); // grava uma nova contagem antes de usa-la
//
//        FirebaseBancoDados.getInstance().gravarContagem(daoSession, mContagemEstoque); // grava uma contagem no firebase
//
//        Bundle argumento = new Bundle();
//        argumento.putLong(ContagemFragment.CODIGO_CONTAGEM, mContagemEstoque.getId()); // registra no parametro o id da nova contagem
//        vInicioApp.setArgumentoBundle(argumento); // envia o argumento
//
//        NavegacaoApp.abrirTelaContagem(vInicioApp.requireParentFragment().getView());
//    }
//
//    @Override
//    /** Resultado recebido do dialog
//     * @param tipoResultado TipoResultado - tipo de resultado obtido no dialog
//     * @param iModel IModel - model do item alterado ou excluido */
//    public void setResultadoDialog(TipoResultado tipoResultado, IModel iModel) {
//        switch (tipoResultado) {
//            case CONFIRMAR_EXCLUSAO: // excluir contagem de estoque
//                vInicioApp.setContagemExcluida((ContagemEstoque) iModel);
//                dContagemEstoque.delete((ContagemEstoque) iModel);
//                vInicioApp.atualizarRecyclerAdapter();
//                FirebaseBancoDados.getInstance().removerContagem(daoSession, (ContagemEstoque) iModel);
//                break;
//
//            case CANCELAR_EXCLUSAO: // cancelar exclusao de contagem de estoque
//                vInicioApp.atualizarRecyclerAdapter();
//        }
//    }
}
