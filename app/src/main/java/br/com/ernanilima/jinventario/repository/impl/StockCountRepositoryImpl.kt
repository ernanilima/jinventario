package br.com.ernanilima.jinventario.repository.impl

import android.annotation.SuppressLint
import android.database.Cursor
import br.com.ernanilima.jinventario.extension.common.getDate
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
        update(stockCount)
    }

    override fun update(stockCount: StockCount) {
        stockCountDao.save(stockCount)
    }

    @SuppressLint("Range")
    override fun findAll(): List<StockCount> {
        val listStockCount: MutableList<StockCount> = mutableListOf()
        val totalStock = "total_stock"

        // select manual, realiza a soma do total de estoque, 0 (zero) como padrao para valor nulo
        val cursor: Cursor = stockCountDao.database.rawQuery(
            // "SELECT STOCK_COUNT.*, COALESCE(SUM(STOCK_COUNT_ITEM.NUMBER_OF_BOXES * STOCK_COUNT_ITEM.NUMBER_PER_BOX), 0) TOTAL FROM STOCK_COUNT LEFT JOIN STOCK_COUNT_ITEM ON STOCK_COUNT.id = STOCK_COUNT_ITEM.STOCK_COUNT GROUP BY STOCK_COUNT.id",
            "SELECT ${StockCountDao.TABLENAME}.*, " +
                    "COALESCE(SUM(${StockCountItemDao.TABLENAME}.${StockCountItemDao.Properties.NumberOfBoxes.columnName} * ${StockCountItemDao.TABLENAME}.${StockCountItemDao.Properties.NumberPerBox.columnName}), 0) $totalStock " +
                    "FROM ${StockCountDao.TABLENAME} LEFT JOIN ${StockCountItemDao.TABLENAME} " +
                    "ON ${StockCountDao.TABLENAME}.${StockCountDao.Properties.Id.columnName} = ${StockCountItemDao.TABLENAME}.${StockCountItemDao.Properties.StockCount.columnName} " +
                    "GROUP BY ${StockCountDao.TABLENAME}.${StockCountDao.Properties.Id.columnName}",
            null
        )

        if (cursor.moveToFirst()) { // se tiver resultado
            do {
                // contagem de estoque
                val stockCount = StockCount(
                    cursor.getLong(cursor.getColumnIndex(StockCountDao.Properties.Id.columnName)),
                    cursor.getDate(cursor.getLong(cursor.getColumnIndex(StockCountDao.Properties.CreationDate.columnName))),
                    cursor.getDate(cursor.getLong(cursor.getColumnIndex(StockCountDao.Properties.UpdateDate.columnName)))
                )

                // total de estoque calculado no select
                stockCount.total = cursor.getLong(cursor.getColumnIndex(totalStock))

                listStockCount.add(stockCount)
            } while (cursor.moveToNext()) // proximo resultado
        }

        return listStockCount
    }

    override fun insertItem(stockCountItem: StockCountItem) {
        updateItem(stockCountItem)
    }

    override fun updateItem(stockCountItem: StockCountItem) {
        stockCountItemsDao.save(stockCountItem)
    }

}