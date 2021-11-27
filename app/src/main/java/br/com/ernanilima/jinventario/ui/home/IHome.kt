package br.com.ernanilima.jinventario.ui.home

import br.com.ernanilima.jinventario.interfaces.IResultadoDialog
import br.com.ernanilima.jinventario.model.StockCount

interface IHome {
    interface IViewModel : IResultadoDialog {
        fun newCount()
        fun listStockCount(): List<StockCount>
        fun updateCount(stockCount: StockCount)
        fun deleteCount(stockCount: StockCount)
    }
}