package br.com.ernanilima.jinventario.presenter;

import br.com.ernanilima.jinventario.interfaces.IContagem;

public class ContagemPresenter implements IContagem.IPresenter {

    private IContagem.IView vContagem;

    /** Construtor
     * @param vContagem IContagem.IView - view(fragment) de contagem */
    public ContagemPresenter(IContagem.IView vContagem) {
        this.vContagem = vContagem;
    }

    @Override
    public void setCodigoContagem(Long idContagem) {
        System.out.println("CODIGO RECEBIDO NO PARAMETRO " + idContagem);
    }

    @Override
    public void adicionarItemColetado() {
        System.out.println("ITEM COLETADO FOI ADICIONADO");
    }
}
