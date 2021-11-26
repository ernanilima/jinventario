package br.com.ernanilima.jinventario.repository.impl

import br.com.ernanilima.jinventario.model.StockCount
import br.com.ernanilima.jinventario.model.StockCountItem
import br.com.ernanilima.jinventario.repository.StockCountRepository
import br.com.ernanilima.jinventario.repository.orm.StockCountDao
import br.com.ernanilima.jinventario.repository.orm.StockCountItemDao
import javax.inject.Inject

class StockCountRepositoryImpl @Inject constructor(
    private val stockCountDao: StockCountDao,
    private val stockCountItemsDao: StockCountItemDao
): StockCountRepository {

    override fun insert(stockCount: StockCount) {
        TODO("Not yet implemented")
    }

    override fun insertItem(stockCountItem: StockCountItem) {
        TODO("Not yet implemented")
    }

    override fun update(stockCount: StockCount) {
        TODO("Not yet implemented")
    }

    override fun updateItem(stockCountItem: StockCountItem) {
        TODO("Not yet implemented")
    }

}