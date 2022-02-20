package br.com.ernanilima.jinventario.repository

import br.com.ernanilima.jinventario.model.StockCount
import br.com.ernanilima.jinventario.model.StockCountItem

interface StockCountRepository {
    fun findStockCountById(id: Long): StockCount
    fun insert(stockCount: StockCount)
    fun update(stockCount: StockCount)
    fun findAll(): List<StockCount>
    fun delete(stockCount: StockCount)
    fun findItemsListByStockCountId(idStockCount: Long): List<StockCountItem>
    fun insertItem(stockCountItem: StockCountItem)
    fun updateItem(stockCountItem: StockCountItem)
    fun deleteItem(stockCountItem: StockCountItem)
}