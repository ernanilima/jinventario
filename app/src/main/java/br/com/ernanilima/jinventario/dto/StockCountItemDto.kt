package br.com.ernanilima.jinventario.dto

import br.com.ernanilima.jinventario.model.StockCountItem

/**
 * DTO usado para gravar os itens no banco de dados do firebase
 */
class StockCountItemDto(stockCountItem: StockCountItem) {
    val barcode: String = stockCountItem.barcode
    val unitPrice: Double = stockCountItem.unitPrice
    val numberOfBoxes: String = stockCountItem.numberOfBoxes
    val numberPerBox: String = stockCountItem.numberPerBox
}