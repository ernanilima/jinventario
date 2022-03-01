package br.com.ernanilima.jinventario.ui.stockCount

import br.com.ernanilima.jinventario.model.StockCountItem

interface IStockCount {
    interface IFragment {
        fun updateItem(stockCountItem: StockCountItem)
        fun deleteItemBySwipe(stockCountItem: StockCountItem)
    }

    interface IViewModel {
        fun setIdStockCount(idStockCount: Long)
        fun openCameraScanner()
        fun newItem(stockCountItem: StockCountItem)
        fun updateItem(stockCountItem: StockCountItem)
        fun deleteItem(stockCountItem: StockCountItem)
    }
}