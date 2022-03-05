package br.com.ernanilima.jinventario.adapter

import android.view.Gravity
import br.com.ernanilima.jinventario.model.StockCount
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.databinding.ItemCountsBinding
import br.com.ernanilima.jinventario.model.Settings
import br.com.ernanilima.jinventario.ui.AppActivity
import br.com.ernanilima.jinventario.ui.home.IHome
import br.com.ernanilima.jinventario.util.Format
import br.com.ernanilima.jinventario.util.Utils

class HomeRecyclerAdapter constructor(
    private val homeViewFragment: IHome.IFragment,
    private val listStockCount: List<StockCount>
) : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>(), IRecyclerAdapter {

    // Carrega as configuracoes
    private val settings: Settings? = AppActivity.settings

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_counts, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userSettings()
        holder.bind(listStockCount[position], homeViewFragment)
    }

    override fun getItemCount(): Int {
        return listStockCount.size
    }

    /**
     * Recebe a posicao do item que deseja deletar
     * @param position Int - posicao que deseja delatar
     */
    override fun notifyItemRemovedBySwipe(position: Int) {
        val stockCount = listStockCount[position]
        stockCount.index = position
        homeViewFragment.deleteCountBySwipe(stockCount)
    }

    /**
     * Deleta o item recebido
     * Notifica o recycler view com o item deletado
     * @param stockCount StockCount
     */
    fun notifyItemRemoved(stockCount: StockCount) {
        (listStockCount as MutableList).removeAt(stockCount.index)
        notifyItemRemoved(stockCount.index)
    }

    /**
     * ViewHolder
     */
    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemCountsBinding = ItemCountsBinding.bind(itemView)

        fun userSettings() {
            if (settings == null || !settings.showPrice) {
                // desativa a opcao de exibir o preco do produto
                Utils.disableOption(binding.layoutTotalPrice)
                binding.layoutTotalQuantity.gravity = Gravity.END
            }
        }

        fun bind(stockCount: StockCount, homeViewFragment: IHome.IFragment) {
            binding.fieldCountNumber.text = stockCount.id.toString()
            binding.fieldCreationDate.text = Format.DATE.format(stockCount.creationDate)
            binding.fieldDateHourUpdate.text = Format.DATE_TIME.format(stockCount.updateDate)
            binding.fieldTotalQuantity.text = stockCount.totalQuantity.toString()
            binding.fieldTotalPrice.text = Format.PRICE_PTBR.format(stockCount.totalPrice).replace("R$ ", "")
            binding.btnEdit.setOnClickListener { homeViewFragment.updateCount(stockCount) }
        }
    }
}