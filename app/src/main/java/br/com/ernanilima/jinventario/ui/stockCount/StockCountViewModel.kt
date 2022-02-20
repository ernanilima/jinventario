package br.com.ernanilima.jinventario.ui.stockCount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseDatabase
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import br.com.ernanilima.jinventario.model.StockCount
import br.com.ernanilima.jinventario.model.StockCountItem
import br.com.ernanilima.jinventario.repository.StockCountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockCountViewModel @Inject constructor(
    private val stockCountDao: StockCountRepository
) : ViewModel(), IStockCount.IViewModel {

    private var _stockCount: StockCount? = null
    val stockCount get() = _stockCount!!

    private var _stockCountItem: StockCountItem? = null
    val stockCountItem get() = _stockCountItem!!

    private var _listStockCountItem: List<StockCountItem> = ArrayList()
    val listStockCountItem get() = _listStockCountItem

    private val _countResult = MutableLiveData<IResultType>()
    val countResult: LiveData<IResultType> = _countResult

    override fun setIdStockCount(idStockCount: Long) {
        _stockCount = stockCountDao.findStockCountById(idStockCount)
        _listStockCountItem = stockCountDao.findItemsListByStockCountId(idStockCount)
        _countResult.postValue(ResultTypeLocal.LIST_STOCK_COUNT_ITEM)
    }

    override fun listItemStockCount(): List<StockCountItem> {
        return listStockCountItem
    }

    override fun newItem(stockCountItem: StockCountItem) {
        stockCountItem.stockCount = stockCount.id
        stockCountDao.insertItem(stockCountItem)
        (listStockCountItem as MutableList).add(0, stockCountItem)
        updateStockCountFirebase()
        _countResult.postValue(ResultTypeLocal.NEW_STOCK_COUNT_ITEM)
    }

    override fun updateItem(stockCountItem: StockCountItem) {
        TODO("Not yet implemented")
    }

    override fun deleteItem(stockCountItem: StockCountItem?) {
        if (stockCountItem != null) {
            this._stockCountItem = stockCountItem
            _countResult.postValue(ResultTypeLocal.DELETE_STOCK_COUNT_ITEM)
        } else {
            stockCountDao.deleteItem(this.stockCountItem)
            updateStockCountFirebase()
        }
    }

    private fun updateStockCountFirebase() {
        FirebaseDatabase.saveStockCountItems(stockCount, listStockCountItem)
    }
}