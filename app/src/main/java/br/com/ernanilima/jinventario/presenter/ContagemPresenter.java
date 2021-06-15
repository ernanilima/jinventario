package br.com.ernanilima.jinventario.presenter;

import android.os.Bundle;

import java.util.Date;
import java.util.List;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.firebase.FirebaseBancoDados;
import br.com.ernanilima.jinventario.interfaces.IContagem;
import br.com.ernanilima.jinventario.model.Configuracao;
import br.com.ernanilima.jinventario.model.ConfiguracaoDao;
import br.com.ernanilima.jinventario.model.ContagemEstoque;
import br.com.ernanilima.jinventario.model.ContagemEstoqueDao;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.IModel;
import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.model.ItemContagemDao;
import br.com.ernanilima.jinventario.service.component.CompartilharArquivo;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.util.Utils;
import br.com.ernanilima.jinventario.view.dialog.AlteracaoDialogFragment;
import br.com.ernanilima.jinventario.view.dialog.CameraDialogFragment;
import br.com.ernanilima.jinventario.view.dialog.ExclusaoDialogFragment;
import br.com.ernanilima.jinventario.view.dialog.TipoResultado;
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado;

public class ContagemPresenter implements IContagem.IPresenter {

    private IContagem.IView vContagem;
    private ContagemEstoque mContagemEstoque;
    private DaoSession daoSession;
    private ContagemEstoqueDao dContagemEstoque;
    private ItemContagemDao dItemContagem;
    private ConfiguracaoDao dConfiguracao;
    private List<ItemContagem> lsItensContagem;

    private Configuracao mConfiguracao;

    /** Construtor
     * @param vContagem IContagem.IView - view(fragment) de contagem */
    public ContagemPresenter(IContagem.IView vContagem) {
        this.vContagem = vContagem;

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vContagem.requireParentFragment().getActivity().getApplication()).getSessao();
        this.dContagemEstoque = daoSession.getContagemEstoqueDao();
        this.dItemContagem = daoSession.getItemContagemDao();
        this.dConfiguracao = daoSession.getConfiguracaoDao();
    }

    @Override
    /** Recebe o id da contagem */
    public void setCodigoContagem(Long idContagem) {
        // busca contagem pelo id e atribui o resultado ao model criado
        mContagemEstoque = dContagemEstoque.load(idContagem);
        // envia a lista de itens para o recycler adapter que sera utilizado no recycler view
        vContagem.setRecyclerAdapter(getLsItensContagem());
        vContagem.setIdParaExibirNoTitulo(idContagem);
    }

    @Override
    /** Abre a camera scanner
     * Envia esse presenter para obter a resposta da camera */
    public void abrirCameraScanner() {
        CameraDialogFragment dCameraFragment = new CameraDialogFragment(this);
        Bundle argumento = new Bundle();
        // armazena a interface como argumento para que possa ser receptado pelo dialog de scanner com a canera
        argumento.putSerializable(CameraDialogFragment.IRESULTADO_CAMERA, this);
        dCameraFragment.setArguments(argumento);
        dCameraFragment.setCancelable(false);
        dCameraFragment.show(vContagem.requireParentFragment().getParentFragmentManager(), "tag");
    }

    @Override
    /** Adiciona o item na contagem */
    public void adicionarItemColetado() {
        if (validarCampos()) {
            ItemContagem mItemContagem = new ItemContagem(
                    null,
                    mContagemEstoque.getId(),
                    vContagem.getCampoCodbarras().getEditText().getText().toString(),
                    vContagem.getCampoQtdDeCaixa().getEditText().getText().toString(),
                    vContagem.getCampoQtdPorCaixa().getEditText().getText().toString()
            );

            dItemContagem.save(mItemContagem); // grava o item coletado
            lsItensContagem.add(0, mItemContagem); // adiciona o item coletado no inicio da lista

            atualizarContagem();

            Utils.limparCampo(vContagem.getCampoCodbarras().getEditText(), vContagem.getCampoQtdDeCaixa().getEditText(),
                    vContagem.getCampoQtdPorCaixa().getEditText());
            vContagem.getCampoCodbarras().getEditText().requestFocus();
        }
    }

    @Override
    /** Exibe um dialog para alterar o item selecionado */
    public void alterarItemColetado(ItemContagem mItemContagem) {
        AlteracaoDialogFragment dAlteracaoFragment = new AlteracaoDialogFragment(this);
        Bundle argumento = new Bundle();
        // armazena o model como argumento para que possa ser receptado pelo dialog de alteracao
        argumento.putSerializable(AlteracaoDialogFragment.MODEL_ITEM_CONTAGEM, mItemContagem);
        argumento.putBoolean(AlteracaoDialogFragment.CAMERA_SCANNER, mConfiguracao == null || mConfiguracao.getCameraScanner());
        dAlteracaoFragment.setArguments(argumento);
        dAlteracaoFragment.setCancelable(false);
        dAlteracaoFragment.show(vContagem.requireParentFragment().getParentFragmentManager(),"tag");
    }

    @Override
    public void excluirItemColetado(ItemContagem mItemContagem) {
        ExclusaoDialogFragment dExclusaoFragment = new ExclusaoDialogFragment(this);
        Bundle argumento = new Bundle();
        // armazena o model como argumento para que possa ser receptado pelo dialog de exclusao
        argumento.putSerializable(ExclusaoDialogFragment.MODEL_ITEM_CONTAGEM, mItemContagem);
        dExclusaoFragment.setArguments(argumento);
        dExclusaoFragment.setCancelable(false);
        dExclusaoFragment.show(vContagem.requireParentFragment().getParentFragmentManager(),"tag");
    }

    /** Busca/Registra a lista de itens buscando no banco greendao
     * @return List<ItemContagem> - lista de itens da contagem */
    private List<ItemContagem> getLsItensContagem() {
        return lsItensContagem = dItemContagem.queryBuilder().orderDesc(ItemContagemDao.Properties.Id)
                .where(ItemContagemDao.Properties.IdContagem.eq(mContagemEstoque.getId())).list();
    }

    /** Atualiza o Recycler Adapter apos um item ser coletado/alterado
     * Atualiza os dados da contagem e grava seus dados no banco greendao */
    private void atualizarContagem() {
        atualizarContagemFirebase();
        vContagem.atualizarRecyclerAdapter(); // atualiza o recycler adapter no fragment
        mContagemEstoque.setDataAlteracao(new Date(System.currentTimeMillis())); // atualiza a contagem com a data/hora alterada
        double quantidade = 0;
        for (ItemContagem mItemContagem : lsItensContagem) {
            // registra o total de todos os itens coletados
            quantidade += Double.parseDouble(mItemContagem.getQtdTotal());
        }
        mContagemEstoque.setQtdTotalItens(String.valueOf(quantidade)); // atualiza a contagem com o total de itens ja coletados
        dContagemEstoque.update(mContagemEstoque); // grava a atualizacao da contagem no banco greendao
    }

    /** Atualiza a lista de itens de contagem no firebase */
    private void atualizarContagemFirebase() {
        // caso um itens seja apagado, essa atualizacao tambem eh realizada no firebase
        FirebaseBancoDados.getInstance().gravarListaItensAlteradosColetados(daoSession, mContagemEstoque, lsItensContagem);
    }

    @Override
    /** Compartilha a contagem em formato csv */
    public void toolbarCompartilhar() {
        if (lsItensContagem.isEmpty()) {
            ToastPersonalizado.erro(vContagem.requireParentFragment().getActivity(), MensagensAlerta.COMPARTILHAMENTO_VAZIO.getMsg());
        } else {
            CompartilharArquivo.csv(vContagem, mContagemEstoque.getId(), lsItensContagem);
        }
    }

    @Override
    /** Carregado ao iniciar o view de contagem */
    public void popularDadosConfiguracao() {
        mConfiguracao = dConfiguracao.load(1L);
        if (mConfiguracao != null && !mConfiguracao.getCameraScanner()) {
            // desativa o botao de usar camera como scanner
            vContagem.desativarUsoDaCamera();
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vContagem.getCampoCodbarras(), MensagensAlerta.CODBARRAS_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(vContagem.getCampoQtdDeCaixa(), MensagensAlerta.QUANTIDADE_DE_CAIXA_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(vContagem.getCampoQtdPorCaixa(), MensagensAlerta.QUANTIDADE_POR_CAIXA_INVALIDO.getMsg());
    }

    @Override
    /** Resultado recebido do dialog
     * @param tipoResultado TipoResultado - tipo de resultado obtido no dialog
     * @param iModel IModel - model do item alterado ou excluido */
    public void setResultadoDialog(TipoResultado tipoResultado, IModel iModel) {
        switch (tipoResultado) {
            case CONFIRMAR_ALTERACAO: // alteracao de item coletado
                vContagem.setItemAlterado((ItemContagem) iModel); // envia o item alterado para atualiza o recycler adapter
                dItemContagem.update((ItemContagem) iModel); // grava a atualizacao no banco greendao
                atualizarContagem();
                break;

            case CONFIRMAR_EXCLUSAO: // excluir item coletado
                vContagem.setItemExcluido((ItemContagem) iModel);
                dItemContagem.delete((ItemContagem) iModel);
                atualizarContagem();
                break;

            case CANCELAR_EXCLUSAO: // cancelar exclusao de item coletado
                vContagem.atualizarRecyclerAdapter();
        }
    }

    @Override
    /** Resultado recebido da camera scanner */
    public void setResultadoCameraScanner(String codigoBarras) {
        vContagem.getCampoCodbarras().getEditText().setText(codigoBarras);
        vContagem.getCampoQtdDeCaixa().requestFocus();
    }
}
