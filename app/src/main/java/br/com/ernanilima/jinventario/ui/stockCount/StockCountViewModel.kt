package br.com.ernanilima.jinventario.ui.stockCount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseDatabase
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import br.com.ernanilima.jinventario.data.result.ResultTypeSettings
import br.com.ernanilima.jinventario.model.Settings
import br.com.ernanilima.jinventario.model.StockCount
import br.com.ernanilima.jinventario.model.StockCountItem
import br.com.ernanilima.jinventario.repository.StockCountRepository
import br.com.ernanilima.jinventario.ui.AppActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockCountViewModel @Inject constructor(
    private val stockCountDao: StockCountRepository,
) : ViewModel(), IStockCount.IViewModel {

    private var _stockCount: StockCount? = null
    val stockCount get() = _stockCount!!

    private var _listStockCountItem: List<StockCountItem> = ArrayList()
    val listStockCountItem get() = _listStockCountItem

    // Carrega as configuracoes
    val settings: Settings? = AppActivity.settings

    private val _settingsResult = MutableLiveData<List<ResultTypeSettings>>()
    val settingsResult: LiveData<List<ResultTypeSettings>> = _settingsResult

    private val _countResult = MutableLiveData<IResultType>()
    val countResult: LiveData<IResultType> = _countResult

    private val _refreshTotalResult = MutableLiveData<IResultType>()
    val refreshTotalResult: LiveData<IResultType> = _refreshTotalResult

    init {
        userSettings()
    }

    private fun userSettings() {
        val userSettings: List<ResultTypeSettings> = ArrayList()
        if (settings != null && !settings.cameraScanner)
            // desativa o botao de usar camera como scanner
            (userSettings as MutableList).add(ResultTypeSettings.DONT_USE_CAMERA)


        if (settings == null || !settings.showPrice)
            // desativa a opcao de inserir o preco do produto
            (userSettings as MutableList).add(ResultTypeSettings.DONT_USE_PRICE)

        _settingsResult.postValue(userSettings)
    }

    override fun setIdStockCount(idStockCount: Long) {
        _stockCount = stockCountDao.findStockCountById(idStockCount)
        _listStockCountItem = stockCountDao.findItemsListByStockCountId(idStockCount)
        _countResult.postValue(ResultTypeLocal.LIST_STOCK_COUNT_ITEM)
        refreshTotal()
    }

    private fun refreshTotal() {
        var totalQuantity: Long = 0
        var totalPrice: Double = 0.0
        listStockCountItem.forEach { item ->
            totalQuantity += item.totalQuantity.toLong()
            totalPrice += item.totalPrice
        }
        _stockCount!!.totalQuantity = totalQuantity
        _stockCount!!.totalPrice = totalPrice
        _refreshTotalResult.postValue(ResultTypeLocal.REFRESH_STOCK_COUNT)
    }

    override fun openCameraScanner() {
        if (settings == null || settings.cameraScannerMlkit) {
            // usar a camera do Google
            _settingsResult.postValue(listOf(ResultTypeSettings.CAMERA_MLKIT))
        } else if (settings.cameraScannerZxing) {
            // usar a camera da Zebra
            _settingsResult.postValue(listOf(ResultTypeSettings.CAMERA_ZXING))
        }
    }

    override fun newItem(stockCountItem: StockCountItem) {
        stockCountItem.stockCount = stockCount.id
        stockCountDao.insertItem(stockCountItem)
        (listStockCountItem as MutableList).add(0, stockCountItem)
        refreshTotal()
        updateStockCountFirebase()
        _countResult.postValue(ResultTypeLocal.NEW_STOCK_COUNT_ITEM)
    }

    override fun updateItem(stockCountItem: StockCountItem) {
        stockCountDao.updateItem(stockCountItem)
        refreshTotal()
        updateStockCountFirebase()
    }

    override fun deleteItem(stockCountItem: StockCountItem) {
        stockCountDao.deleteItem(stockCountItem)
        refreshTotal()
        updateStockCountFirebase()
    }

    private fun updateStockCountFirebase() {
        FirebaseDatabase.saveStockCountItems(stockCount, listStockCountItem)
    }
}