package br.com.ernanilima.jinventario.interfaces;

import br.com.ernanilima.jinventario.model.IModel;
import br.com.ernanilima.jinventario.view.dialog.TipoResultado;

/** Resultado do dialog */
public interface IResultadoDialog {

    /** @param tipoResultado TipoResultado - tipo de resultado obtido no dialog
     * @param iModel IModel - model do item alterado ou excluido */
    void setResultadoDialog(TipoResultado tipoResultado, IModel iModel);
}
