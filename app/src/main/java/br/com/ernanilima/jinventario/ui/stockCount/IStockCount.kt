package br.com.ernanilima.jinventario.ui.stockCount

import br.com.ernanilima.jinventario.model.StockCountItem

interface IStockCount {
    interface IViewModel {
        fun setIdStockCount(idStockCount: Long)
        fun listItemStockCount(): List<StockCountItem>
        fun openCameraScanner()
        fun newItem(stockCountItem: StockCountItem)
        fun updateItem(stockCountItem: StockCountItem)
        fun deleteItem(stockCountItem: StockCountItem? = null)
    }
}