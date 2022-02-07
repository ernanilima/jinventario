package br.com.ernanilima.jinventario.ui.stockCount

import br.com.ernanilima.jinventario.model.StockCountItem

interface IStockCount {
    interface IViewModel {
        fun listItemStockCount(): List<StockCountItem>
        fun updateItem(stockCountItem: StockCountItem)
        fun deleteItem(stockCountItem: StockCountItem)
    }
}