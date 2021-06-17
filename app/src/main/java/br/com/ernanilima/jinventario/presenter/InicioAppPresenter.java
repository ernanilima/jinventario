package br.com.ernanilima.jinventario.presenter;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import java.util.Date;
import java.util.List;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.firebase.FirebaseAutenticacao;
import br.com.ernanilima.jinventario.firebase.FirebaseBancoDados;
import br.com.ernanilima.jinventario.firebase.IFirebaseAutenticacao;
import br.com.ernanilima.jinventario.interfaces.IInicioApp;
import br.com.ernanilima.jinventario.model.ContagemEstoque;
import br.com.ernanilima.jinventario.dao.ContagemEstoqueDao;
import br.com.ernanilima.jinventario.dao.DaoSession;
import br.com.ernanilima.jinventario.model.IModel;
import br.com.ernanilima.jinventario.service.component.NomeAparelhoAutenticacao;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp;
import br.com.ernanilima.jinventario.view.ContagemFragment;
import br.com.ernanilima.jinventario.view.dialog.ExclusaoDialogFragment;
import br.com.ernanilima.jinventario.view.dialog.TipoResultado;

public class InicioAppPresenter implements IInicioApp.IPresenter {

    private IInicioApp.IView vInicioApp;
    private IFirebaseAutenticacao iFirebaseAutenticacao;
    private DaoSession daoSession;
    private ContagemEstoqueDao dContagemEstoque;

    /** Construtor
     * @param vInicioApp IInicioApp.IView - view(fragment) do inicio do app */
    public InicioAppPresenter(IInicioApp.IView vInicioApp) {
        this.vInicioApp = vInicioApp;
        iFirebaseAutenticacao = new FirebaseAutenticacao();

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vInicioApp.requireParentFragment().getActivity().getApplication()).getSessao();
        this.dContagemEstoque = daoSession.getContagemEstoqueDao();

        // envia o conteudo para utilizacao no header do drawer layout
        vInicioApp.setNomeAparelho(NomeAparelhoAutenticacao.getInstance(daoSession).getNomeAparelho());
        vInicioApp.setEmailUsuario(iFirebaseAutenticacao.getEmailUsuario());
    }

    @Override
    public void novaContagem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(vInicioApp.requireParentFragment().getContext());
        builder.setTitle("Contagem de Estoque")
                .setMessage("Deseja criar uma contagem de estoque?")
                .setPositiveButton("Sim", (dialog, which) -> criarNovaContagem())
                .setNegativeButton("Não", (dialog, which) -> dialog.cancel())
                .setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    /** Busca a lista de contagens buscando no banco greendao
     * @return List<ItemContagem> - lista de itens de contagens */
    public List<ContagemEstoque> getLsContagensEstoque() {
        return dContagemEstoque.queryBuilder().orderDesc(ContagemEstoqueDao.Properties.DataAlteracao).list();
    }

    @Override
    public void alterarContagem(ContagemEstoque mContagemEstoque) {
        Bundle argumento = new Bundle();
        argumento.putLong(ContagemFragment.CODIGO_CONTAGEM, mContagemEstoque.getId());
        vInicioApp.setArgumentoBundle(argumento);

        NavegacaoApp.abrirTelaContagem(vInicioApp.requireParentFragment().getView());
    }

    @Override
    public void excluirContagemEstoque(ContagemEstoque mContagemEstoque) {
        ExclusaoDialogFragment dExclusaoFragment = new ExclusaoDialogFragment(this);
        Bundle argumento = new Bundle();
        // armazena o model como argumento para que possa ser receptado pelo dialog de exclusao
        argumento.putSerializable(ExclusaoDialogFragment.MODEL_ITEM_CONTAGEM, mContagemEstoque);
        dExclusaoFragment.setArguments(argumento);
        dExclusaoFragment.setCancelable(false);
        dExclusaoFragment.show(vInicioApp.requireParentFragment().getParentFragmentManager(),"tag");
    }

    private void criarNovaContagem() {
        Date dataAtual = new Date(System.currentTimeMillis());
        ContagemEstoque mContagemEstoque = new ContagemEstoque(null, dataAtual, dataAtual, "0");

        dContagemEstoque.save(mContagemEstoque); // grava uma nova contagem antes de usa-la

        FirebaseBancoDados.getInstance().gravarContagem(daoSession, mContagemEstoque); // grava uma contagem no firebase

        Bundle argumento = new Bundle();
        argumento.putLong(ContagemFragment.CODIGO_CONTAGEM, mContagemEstoque.getId()); // registra no parametro o id da nova contagem
        vInicioApp.setArgumentoBundle(argumento); // envia o argumento

        NavegacaoApp.abrirTelaContagem(vInicioApp.requireParentFragment().getView());
    }

    @Override
    /** Resultado recebido do dialog
     * @param tipoResultado TipoResultado - tipo de resultado obtido no dialog
     * @param iModel IModel - model do item alterado ou excluido */
    public void setResultadoDialog(TipoResultado tipoResultado, IModel iModel) {
        switch (tipoResultado) {
            case CONFIRMAR_EXCLUSAO: // excluir contagem de estoque
                vInicioApp.setContagemExcluida((ContagemEstoque) iModel);
                dContagemEstoque.delete((ContagemEstoque) iModel);
                vInicioApp.atualizarRecyclerAdapter();
                FirebaseBancoDados.getInstance().removerContagem(daoSession, (ContagemEstoque) iModel);
                break;

            case CANCELAR_EXCLUSAO: // cancelar exclusao de contagem de estoque
                vInicioApp.atualizarRecyclerAdapter();
        }
    }
}
