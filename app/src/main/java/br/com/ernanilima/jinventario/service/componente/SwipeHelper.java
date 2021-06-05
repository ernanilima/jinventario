package br.com.ernanilima.jinventario.service.componente;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.ernanilima.jinventario.adapter.IRecycleAdapter;

/** Configura a opcao de deslizar o item no recycle view */
public class SwipeHelper extends ItemTouchHelper.Callback {

    private ItemTouchHelper itemTouchHelper;
    private final ColorDrawable background = new ColorDrawable(Color.RED); // cor do background no item deslizado
    private IRecycleAdapter iRecycleAdapter;

    public void setRecycleView(RecyclerView recycle_view) {
        this.itemTouchHelper = new ItemTouchHelper(this);
        this.itemTouchHelper.attachToRecyclerView(recycle_view);
        this.iRecycleAdapter = (IRecycleAdapter) recycle_view.getAdapter();
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // define o tipo de movimentacao aceito pelo slider
        return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        // usado para mover o item por toda a tela
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // ao executar o slider totalmente
        iRecycleAdapter.setPosicaoParaExcluir(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        // define o background do item
        background.setBounds(0, viewHolder.itemView.getTop(), viewHolder.itemView.getLeft() + ((int)dX), viewHolder.itemView.getBottom());
        background.draw(c);
    }
}
