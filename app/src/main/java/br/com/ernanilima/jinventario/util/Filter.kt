package br.com.ernanilima.jinventario.util

object Filter {

    /**
     * Possibilita que filtre conteudo para numero double
     * Usado principalmente para preco
     * Converte todas as virgulas(,) para pontos(.)
     * Se nulo ou vazio, retorna 0.00
     * @param value String? - Conteudo a ser filtrado
     * @return double - conteudo filtrado para double
     */
    fun toDouble(value: String?): Double {
        if (value == null) return 0.00
        val valueToDouble = value.replace(Regex("[^0-9|^,]"), "").replace(",", ".")
        if (valueToDouble == "") return 0.0
        return valueToDouble.toDouble()
    }
}
