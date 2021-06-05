package br.com.ernanilima.jinventario.interfaces;

import br.com.ernanilima.jinventario.model.IModel;
import br.com.ernanilima.jinventario.model.ItemContagem;

/** Resultado do dialog */
public interface IResultadoDialog {
    /** @param mItemContagem ItemContagem - item alterado no dialog */
    void resultadoItemAlteradoDialog(ItemContagem mItemContagem);

    /** @param confirmarCancelar boolean - true se o item for excluido no dialog
     * @param iModel IModel - model do item excluido no dialog */
    void resultadoItemExcluidoDialog(boolean confirmarCancelar, IModel iModel);
}
