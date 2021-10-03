package br.com.ernanilima.jinventario.util;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Formatar {
    /** Formata a data para o padrao brasileiro */
    public static final DateFormat DATA = new SimpleDateFormat("dd/MM/yy");

    /** Formata a data para o padrao brasileiro */
    public static final DateFormat DATA_HORA = new SimpleDateFormat("dd/MM/yy HH:mm");

    /** Formata valores monetarios com o padrao do Brasil.
     * Arredondamento para cima, valores com duas casas decimais */
    public static final DecimalFormat VALOR_RS = new DecimalFormat("\u00A4 ###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    static { VALOR_RS.setRoundingMode(RoundingMode.HALF_UP); }
}
