package br.com.ernanilima.jinventario.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Formatar {
    /** Formata a data para o padrao brasileiro */
    public static final DateFormat DATA = new SimpleDateFormat("dd/MM/yy");

    /** Formata a data para o padrao brasileiro */
    public static final DateFormat DATA_HORA = new SimpleDateFormat("dd/MM/yy HH:mm");
}
