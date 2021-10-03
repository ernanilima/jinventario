package br.com.ernanilima.jinventario.util;

public class Filtro {

    /** Possibilita que filtre conteudo para numero double
     * Usado principalmente para preco
     * Converte todas as virgulas(,) para pontos(.)
     * Se nulo ou vazio, retorna 0.00
     * @param numeroDouble String - Conteudo a ser filtrado
     * @return double - conteudo filtrado para double */
    public static Double pDouble(String numeroDouble) {
        if (numeroDouble == null) { return 0.00; }
        numeroDouble = numeroDouble.replaceAll("[^0-9|^,]", "").replace(",", ".");
        if (numeroDouble.equals("")) { return 0.00; }
        return Double.parseDouble(numeroDouble);
    }
}
