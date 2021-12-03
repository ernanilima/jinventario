package br.com.ernanilima.jinventario.util

import android.annotation.SuppressLint
import java.math.RoundingMode
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object Format {
    /**
     * Data para o padrao brasileiro
     */
    val DATE: DateFormat = SimpleDateFormat("dd/MM/yy")

    /**
     * Data e hora para o padrao brasileiro
     */
    val DATE_TIME: DateFormat = SimpleDateFormat("dd/MM/yy HH:mm")

    /**
     * Valores monetarios com o padrao do Brasil.
     * Arredondamento para cima, valores com duas casas decimais
     */
    @JvmField
    val PRICE_PTBR = DecimalFormat("\u00A4 ###0.00", DecimalFormatSymbols(Locale("pt", "BR")))

    init {
        PRICE_PTBR.roundingMode = RoundingMode.HALF_UP
    }
}