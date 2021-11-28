package br.com.ernanilima.jinventario.repository

import br.com.ernanilima.jinventario.model.StockCount
import br.com.ernanilima.jinventario.model.StockCountItem

interface StockCountRepository {
    fun insert(stockCount: StockCount)
    fun update(stockCount: StockCount)
    fun findAll(): List<StockCount>
    fun delete(stockCount: StockCount)
    fun insertItem(stockCountItem: StockCountItem)
    fun updateItem(stockCountItem: StockCountItem)
}