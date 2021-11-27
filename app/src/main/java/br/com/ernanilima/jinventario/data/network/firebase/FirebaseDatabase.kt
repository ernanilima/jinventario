package br.com.ernanilima.jinventario.data.network.firebase

import br.com.ernanilima.jinventario.dto.StockCountItemDto
import br.com.ernanilima.jinventario.model.*
import br.com.ernanilima.jinventario.ui.AppActivity
import br.com.ernanilima.jinventario.util.Utils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseDatabase {

    private val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val user: User = AppActivity.user!!

    /**
     * Inserir/Atualizar os itens da contagem de estoque
     * @param stockCount StockCount - contagem de estoque
     * @param listStockCountItem List<StockCountItem> - itens da contatem de estoque
     */
    fun saveStockCountItems(stockCount: StockCount, listStockCountItem: List<StockCountItem>) {
        if (listStockCountItem.isEmpty()) {
            saveStockCount(stockCount)

        } else {
            // utiliza o dto para enviar apenas os dados necessarios
            val listStockCountItemDto: MutableList<StockCountItemDto> = mutableListOf()
            for (stockCountItem in listStockCountItem) {
                listStockCountItemDto.add(StockCountItemDto(stockCountItem))
            }
            baseStockCount(stockCount).setValue(listStockCountItemDto)
        }
    }

    /**
     * Inserir/Atualizar uma contagem de estoque
     * O proprio firebase verifica se existe
     * @param stockCount StockCount - contagem de estoque
     */
    fun saveStockCount(stockCount: StockCount) {
        baseStockCount(stockCount).setValue("Contagem N˚ " + stockCount.id)
    }

    /**
     * Deletar uma contagem de estoque
     * O proprio firebase verifica se existe
     * @param stockCount StockCount - contagem de estoque
     */
    fun deleteStockCount(stockCount: StockCount) {
        baseStockCount(stockCount).removeValue()
    }

    /**
     * @param stockCount StockCount - contagem de estoque
     * @return DatabaseReference - referencia da contagem de estoque
     */
    private fun baseStockCount(stockCount: StockCount): DatabaseReference {
        return reference
                // e-mail do usuario
                .child(Utils.converter(user.email))
                // nome do aparelho
                .child(user.deviceName)
                // id da contagem
                .child(stockCount.id.toString())
    }
}
