package br.com.ernanilima.jinventario.adapter

import br.com.ernanilima.jinventario.model.StockCount
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.databinding.ItemCountsBinding
import br.com.ernanilima.jinventario.ui.home.IHome
import br.com.ernanilima.jinventario.util.Format

class HomeRecyclerAdapter constructor(
    private val homeViewFragment: IHome.IFragment,
    private val listStockCount: List<StockCount>
) : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>(), IRecyclerAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_counts, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

        fun bind(stockCount: StockCount, homeViewFragment: IHome.IFragment) {
            binding.fieldCountNumber.text = stockCount.id.toString()
            binding.fieldCreationDate.text = Format.DATE.format(stockCount.creationDate)
            binding.fieldDateHourUpdate.text = Format.DATE_TIME.format(stockCount.updateDate)
            binding.fieldTotal.text = stockCount.totalQuantity.toString()
            binding.btnEdit.setOnClickListener { homeViewFragment.updateCount(stockCount) }
        }
    }
}