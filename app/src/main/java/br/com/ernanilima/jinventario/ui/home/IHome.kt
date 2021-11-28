package br.com.ernanilima.jinventario.ui.home

import br.com.ernanilima.jinventario.model.StockCount

interface IHome {
    interface IViewModel {
        fun newCount()
        fun listStockCount(): List<StockCount>
        fun updateCount(stockCount: StockCount)
        fun deleteCount(stockCount: StockCount)
        fun deleteCount()
    }
}