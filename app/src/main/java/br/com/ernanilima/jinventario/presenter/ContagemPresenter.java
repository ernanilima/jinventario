package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.config.DbGreenDao;
import br.com.ernanilima.jinventario.interfaces.IContagem;
import br.com.ernanilima.jinventario.model.ContagemEstoque;
import br.com.ernanilima.jinventario.model.ContagemEstoqueDao;
import br.com.ernanilima.jinventario.model.DaoSession;
import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.model.ItemContagemDao;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.util.Utils;

public class ContagemPresenter implements IContagem.IPresenter {

    private IContagem.IView vContagem;
    private ContagemEstoque mContagemEstoque;
    private DaoSession daoSession;
    private ContagemEstoqueDao dContagemEstoque;
    private ItemContagemDao dItemContagem;

    /** Construtor
     * @param vContagem IContagem.IView - view(fragment) de contagem */
    public ContagemPresenter(IContagem.IView vContagem) {
        this.vContagem = vContagem;

        // GREENDAO
        this.daoSession = ((DbGreenDao) this.vContagem.requireParentFragment().getActivity().getApplication()).getSessao();
        this.dContagemEstoque = daoSession.getContagemEstoqueDao();
        this.dItemContagem = daoSession.getItemContagemDao();
    }

    @Override
    /** Recebe o id da contagem */
    public void setCodigoContagem(Long idContagem) {
        // busca contagem pelo id e atribui o resultado ao model criado
        mContagemEstoque = dContagemEstoque.load(idContagem);
        System.out.println("CODIGO RECEBIDO NO PARAMETRO " + idContagem);
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

            Utils.limparCampo(vContagem.getCampoCodbarras().getEditText(), vContagem.getCampoQtdDeCaixa().getEditText(),
                    vContagem.getCampoQtdPorCaixa().getEditText());
            vContagem.getCampoCodbarras().getEditText().requestFocus();
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(vContagem.getCampoCodbarras(), MensagensAlerta.CODBARRAS_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(vContagem.getCampoQtdDeCaixa(), MensagensAlerta.QUANTIDADE_DE_CAIXA_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(vContagem.getCampoQtdPorCaixa(), MensagensAlerta.QUANTIDADE_POR_CAIXA_INVALIDO.getMsg());
    }
}
