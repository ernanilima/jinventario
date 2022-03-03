package br.com.ernanilima.jinventario.repository.impl

import android.annotation.SuppressLint
import android.database.Cursor
import br.com.ernanilima.jinventario.extension.common.getDate
import br.com.ernanilima.jinventario.model.StockCount
import br.com.ernanilima.jinventario.model.StockCountItem
import br.com.ernanilima.jinventario.repository.StockCountRepository
import br.com.ernanilima.jinventario.repository.orm.StockCountDao
import br.com.ernanilima.jinventario.repository.orm.StockCountItemDao
import java.util.*
import javax.inject.Inject

class StockCountRepositoryImpl @Inject constructor(
    private val stockCountDao: StockCountDao,
    private val stockCountItemDao: StockCountItemDao
): StockCountRepository {

    override fun findStockCountById(id: Long): StockCount {
        return stockCountDao.load(id)
    }

    override fun insert(stockCount: StockCount) {
        val currentDate = Date(System.currentTimeMillis())
        stockCount.id = null
        stockCount.creationDate = currentDate
        stockCount.updateDate = currentDate
        stockCountDao.save(stockCount)
    }

    override fun update(stockCount: StockCount) {
        stockCount.updateDate = Date(System.currentTimeMillis())
        stockCountDao.save(stockCount)
    }

    @SuppressLint("Range")
    override fun findAll(): List<StockCount> {
        val listStockCount: MutableList<StockCount> = mutableListOf()

        val TAB_COUNT = StockCountDao.TABLENAME // nome da tabela de contagem de estoque
        val TAB_ITEMS = StockCountItemDao.TABLENAME // nome da tabela com items da contagem de estoque
        val COL_NOFBXS = StockCountItemDao.Properties.NumberOfBoxes.columnName // nome da coluna quantidade de caixas
        val COL_NPERBX = StockCountItemDao.Properties.NumberPerBox.columnName // nome da coluna quantidade por caixa
        val TOTAL_ITEMS = "total_items" // constante que armazenara o total de itens de cada contagem de estoque
        val COL_ITEMS_PRC = StockCountItemDao.Properties.UnitPrice.columnName // nome da coluna com preco unitario do item
        val TOTAL_PRICE = "total_price" // constante que armazenara o preco total coletado de cada contagem de estoque
        val COL_ID_COUNT = StockCountDao.Properties.Id.columnName // nome da coluna com o id da contagem de estoque
        val COL_ITEM_MCOUNT = StockCountItemDao.Properties.StockCount.columnName // nome da coluna que faz o relacionamento com a contagem de estoque
        val COL_COUNT_CREATION = StockCountDao.Properties.CreationDate.columnName // nome da coluna que grava a data de criacao da contagem de estoque
        val COL_COUNT_UPDATE = StockCountDao.Properties.UpdateDate.columnName // nome da coluna que grava a data/hora de atualizacao da contagem de estoque

        // select manual, realiza a soma do total de estoque, 0 (zero) como padrao para valor nulo
        val cursor: Cursor = stockCountDao.database.rawQuery(
            // "SELECT STOCK_COUNT.*, COALESCE(SUM(STOCK_COUNT_ITEM.NUMBER_OF_BOXES * STOCK_COUNT_ITEM.NUMBER_PER_BOX), 0) TOTAL FROM STOCK_COUNT LEFT JOIN STOCK_COUNT_ITEM ON STOCK_COUNT.id = STOCK_COUNT_ITEM.STOCK_COUNT GROUP BY STOCK_COUNT.id",
            "SELECT $TAB_COUNT.*, " +
                    "COALESCE(SUM($TAB_ITEMS.$COL_NOFBXS * $TAB_ITEMS.$COL_NPERBX), 0) $TOTAL_ITEMS, " +
                    "COALESCE(SUM($TAB_ITEMS.$COL_NOFBXS * $TAB_ITEMS.$COL_NPERBX * $TAB_ITEMS.$COL_ITEMS_PRC), 0) $TOTAL_PRICE " +
                    "FROM $TAB_COUNT LEFT JOIN $TAB_ITEMS " +
                    "ON $TAB_COUNT.$COL_ID_COUNT = $TAB_ITEMS.$COL_ITEM_MCOUNT " +
                    "GROUP BY $TAB_COUNT.$COL_ID_COUNT " +
                    "ORDER BY $COL_COUNT_UPDATE DESC",
            null
        )

        if (cursor.moveToFirst()) { // se tiver resultado
            do {
                // contagem de estoque
                val stockCount = StockCount(
                    cursor.getLong(cursor.getColumnIndex(COL_ID_COUNT)),
                    cursor.getDate(cursor.getLong(cursor.getColumnIndex(COL_COUNT_CREATION))),
                    cursor.getDate(cursor.getLong(cursor.getColumnIndex(COL_COUNT_UPDATE)))
                )

                // total de estoque calculado no select
                stockCount.totalQuantity = cursor.getLong(cursor.getColumnIndex(TOTAL_ITEMS))
                stockCount.totalPrice = cursor.getDouble(cursor.getColumnIndex(TOTAL_PRICE))

                listStockCount.add(stockCount)
            } while (cursor.moveToNext()) // proximo resultado
        }

        return listStockCount
    }

    override fun delete(stockCount: StockCount) {
        stockCountDao.delete(stockCount)
    }

    override fun findItemsListByStockCountId(idStockCount: Long): List<StockCountItem> {
        return stockCountItemDao.queryBuilder().orderDesc(StockCountItemDao.Properties.Id)
                .where(StockCountItemDao.Properties.StockCount.eq(idStockCount)).list()
    }

    override fun insertItem(stockCountItem: StockCountItem) {
        update(findStockCountById(stockCountItem.stockCount))
        updateItem(stockCountItem)
    }

    override fun updateItem(stockCountItem: StockCountItem) {
        update(findStockCountById(stockCountItem.stockCount))
        stockCountItemDao.save(stockCountItem)
    }

    override fun deleteItem(stockCountItem: StockCountItem) {
        update(findStockCountById(stockCountItem.stockCount))
        stockCountItemDao.delete(stockCountItem)
    }
}