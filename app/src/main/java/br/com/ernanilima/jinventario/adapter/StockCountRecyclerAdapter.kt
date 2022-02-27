package br.com.ernanilima.jinventario.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.databinding.ItemStockCountRecyclerviewBinding
import br.com.ernanilima.jinventario.model.Settings
import br.com.ernanilima.jinventario.model.StockCountItem
import br.com.ernanilima.jinventario.ui.AppActivity
import br.com.ernanilima.jinventario.ui.stockCount.IStockCount
import br.com.ernanilima.jinventario.util.Format
import br.com.ernanilima.jinventario.util.Utils

class StockCountRecyclerAdapter constructor(
    private val stockCountViewModel: IStockCount.IViewModel,
    private val listItemStockCount: List<StockCountItem>
) : RecyclerView.Adapter<StockCountRecyclerAdapter.ViewHolder>(), IRecyclerAdapter {

    // Carrega as configuracoes
    private val settings: Settings? = AppActivity.settings

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockCountRecyclerAdapter.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_stock_count_recyclerview, parent, false)
    )

    override fun onBindViewHolder(holder: StockCountRecyclerAdapter.ViewHolder, position: Int) {
        holder.userSettings()
        holder.bind(listItemStockCount[position], stockCountViewModel)
    }

    override fun getItemCount(): Int {
        return listItemStockCount.size
    }

    /**
     * Recebe a posicao do item que deseja deletar
     * @param position Int - posicao que deseja delatar
     */
    override fun notifyItemRemovedBySwipe(position: Int) {
        val stockCountItem = listItemStockCount[position]
        stockCountItem.index = position
        stockCountViewModel.deleteItem(stockCountItem)
    }

    /**
     * Deleta o item recebido
     * Notifica o recycler view com o item deletado
     * @param stockCountItem StockCountItem
     */
    fun notifyItemRemoved(stockCountItem: StockCountItem) {
        (listItemStockCount as MutableList).removeAt(stockCountItem.index)
        notifyItemRemoved(stockCountItem.index)
    }

    /**
     * ViewHolder
     */
    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemStockCountRecyclerviewBinding = ItemStockCountRecyclerviewBinding.bind(itemView)

        fun userSettings() {
            if (settings == null || !settings.showPrice)
                // desativa a opcao de exibir o preco do produto
                Utils.disableOption(binding.layoutTotalPrice)
        }

        fun bind(stockCountItem: StockCountItem, stockCountViewModel: IStockCount.IViewModel) {
            binding.fieldBarcode.text = stockCountItem.barcode.toString()
            binding.fieldNumberOfBoxes.text = stockCountItem.numberOfBoxes.toString()
            binding.fieldNumberPerBox.text = stockCountItem.numberPerBox.toString()
            binding.fieldTotalQuantity.text = stockCountItem.totalQuantity.toString()
            binding.fieldTotalPrice.text = Format.PRICE_PTBR.format(stockCountItem.totalPrice).replace("R$ ", "")
            binding.btnEdit.setOnClickListener { stockCountViewModel.updateItem(stockCountItem) }
        }
    }
}