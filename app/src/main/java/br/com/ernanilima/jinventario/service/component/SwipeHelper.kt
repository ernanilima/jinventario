package br.com.ernanilima.jinventario.service.component

import android.graphics.Canvas
import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import br.com.ernanilima.jinventario.adapter.IRecyclerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import br.com.ernanilima.jinventario.R

/**
 * Configura a opcao de deslizar o item no recycler view
 */
class SwipeHelper : ItemTouchHelper.Callback() {

    private var itemTouchHelper: ItemTouchHelper? = null
    private var background: ColorDrawable? = null
    private var icon: Drawable? = null
    private var iRecyclerAdapter: IRecyclerAdapter? = null

    /**
     * @param recycler_view RecyclerView - recycler view onde essa classe vai ser utilizada
     */
    fun setRecyclerView(recycler_view: RecyclerView) {
        itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper!!.attachToRecyclerView(recycler_view)
        iRecyclerAdapter = recycler_view.adapter as IRecyclerAdapter
        background = ColorDrawable(Color.RED)
        icon = ContextCompat.getDrawable(recycler_view.context, R.drawable.ic_delete)
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // define o tipo de movimentacao aceito pelo slider
        return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.END)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // usado para mover o item por toda a tela
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // ao executar o slider totalmente
        iRecyclerAdapter!!.notifyItemRemovedBySwipe(viewHolder.adapterPosition)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        // define o background do item
        background!!.setBounds(0, viewHolder.itemView.top, viewHolder.itemView.left + dX.toInt(), viewHolder.itemView.bottom)
        background!!.draw(c)

        val icMargin = (viewHolder.itemView.height - icon!!.intrinsicHeight) / 2
        val icTop = viewHolder.itemView.top + (viewHolder.itemView.height - icon!!.intrinsicHeight) / 2
        val icBottom = icTop + icon!!.intrinsicHeight
        val icLeft = viewHolder.itemView.left + icMargin
        val icRight = icLeft + icon!!.intrinsicWidth

        // define o icone do item
        icon!!.setBounds(icLeft, icTop, icRight, icBottom)
        icon!!.draw(c)
    }
}
