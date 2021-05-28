package br.com.ernanilima.jinventario.interfaces;

import br.com.ernanilima.jinventario.firebase.enun.TipoResultado;

/** Extender essa interface na interface que deseja
 * obter o resultado */
public interface IResultadoFirebase {
    void setResultado(TipoResultado resultado);
}
