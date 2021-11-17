package br.com.ernanilima.jinventario.extension.common

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Referencia [link](https://qastack.com.br/programming/1555262/calculating-the-difference-between-two-java-date-instances)
 */
object WaitingTime {
    const val TEN: Int = 10

    /**
     * @param date Date - data/hora para comparar
     * @param minutes Int - minutos para esperar (10 por padrao)
     * @return String - minutos que deve esperar
     */
    fun get(date: Date, minutes: Int = TEN): Int {
        // diferenca emtre data/hora atual e data/hora do parametro
        // diferenca em milissegundos
        val waitTimeLilliseconds: Long = System.currentTimeMillis() - date.time

        // tempo para esperar
        val waitingTimeMinutes = minutes - TimeUnit.MINUTES.convert(waitTimeLilliseconds, TimeUnit.MILLISECONDS)

        // retorna o tenpo que o usuario deve esperar
        return if (waitingTimeMinutes > 0) waitingTimeMinutes.toInt() else 0
    }
}