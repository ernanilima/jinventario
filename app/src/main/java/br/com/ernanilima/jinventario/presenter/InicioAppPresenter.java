package br.com.ernanilima.jinventario.presenter;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import java.util.Date;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.interfaces.IInicioApp;
import br.com.ernanilima.jinventario.model.ContagemEstoque;
import br.com.ernanilima.jinventario.model.ContagemEstoqueDao;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp;
import br.com.ernanilima.jinventario.view.ContagemFragment;

public class InicioAppPresenter implements IInicioApp.Presenter {

    private IInicioApp.View vInicioApp;
    private DaoSession daoSession;
    private ContagemEstoqueDao dContagemEstoque;

    /** Construtor
     * @param vInicioApp IInicioApp.View - view(fragment) do inicio do app */
    public InicioAppPresenter(IInicioApp.View vInicioApp) {
        this.vInicioApp = vInicioApp;

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vInicioApp.requireParentFragment().getActivity().getApplication()).getSessao();
        this.dContagemEstoque = daoSession.getContagemEstoqueDao();
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

    private void criarNovaContagem() {
        Date dataAtual = new Date(System.currentTimeMillis());
        ContagemEstoque mContagemEstoque = new ContagemEstoque(null, dataAtual, dataAtual, "0");

        dContagemEstoque.save(mContagemEstoque);

        Bundle argumento = new Bundle();
        argumento.putLong(ContagemFragment.CODIGO_CONTAGEM, mContagemEstoque.getId());
        vInicioApp.setArgumentoBundle(argumento);

        NavegacaoApp.abrirTelaContagem(vInicioApp.requireParentFragment().getView());
    }
}
