package br.com.ernanilima.jinventario.ui.stockCount

import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.model.StockCountItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockCountViewModel @Inject constructor(

) : ViewModel(), IStockCount.IViewModel {
    override fun listItemStockCount(): List<StockCountItem> {
        return ArrayList()
    }

    override fun updateItem(stockCountItem: StockCountItem) {
        TODO("Not yet implemented")
    }

    override fun deleteItem(stockCountItem: StockCountItem) {
        TODO("Not yet implemented")
    }
}