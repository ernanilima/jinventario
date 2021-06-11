package br.com.ernanilima.jinventario.interfaces;

import java.io.Serializable;

/** Resultado da camera scanner */
public interface IResultadoCameraScanner extends Serializable {
    long serialVersionUID = 1L;

    /** @param codigoBarras String - codigo de barras escaneado na camera */
    void setResultadoCameraScanner(String codigoBarras);
}
