package br.com.ernanilima.jinventario.view.dialog;

import br.com.ernanilima.jinventario.model.ItemContagem;

/** Resultado do dialog */
public interface IResultadoDialog {
    /** @param mItemContagem ItemContagem - item alterado no dialog */
    void resultadoItemAlteradoDialog(ItemContagem mItemContagem);

    /** @param confirmarCancelar boolean - true se o item for excluido no dialog
     * @param mItemContagem ItemContagem - item excluido no dialog */
    void resultadoItemExcluidoDialog(boolean confirmarCancelar, ItemContagem mItemContagem);
}
