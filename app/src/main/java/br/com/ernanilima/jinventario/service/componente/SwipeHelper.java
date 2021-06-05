package br.com.ernanilima.jinventario.service.componente;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.adapter.IRecyclerAdapter;

/** Configura a opcao de deslizar o item no recycler view */
public class SwipeHelper extends ItemTouchHelper.Callback {

    private ItemTouchHelper itemTouchHelper;
    private ColorDrawable background;
    private Drawable icone;
    private IRecyclerAdapter iRecyclerAdapter;

    /** @param recycler_view RecyclerView - recycler view onde essa classe vai ser utilizada */
    public void setRecyclerView(RecyclerView recycler_view) {
        this.itemTouchHelper = new ItemTouchHelper(this);
        this.itemTouchHelper.attachToRecyclerView(recycler_view);
        this.iRecyclerAdapter = (IRecyclerAdapter) recycler_view.getAdapter();
        this.background = new ColorDrawable(Color.RED); // cor do background no item deslizado
        this.icone =  ContextCompat.getDrawable(recycler_view.getContext(), R.drawable.ic_excluir); // icone no item deslizado
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
        iRecyclerAdapter.setPosicaoParaExcluir(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        // define o background do item
        background.setBounds(0, viewHolder.itemView.getTop(), viewHolder.itemView.getLeft() + ((int)dX), viewHolder.itemView.getBottom());
        background.draw(c);

        int icMargem = (viewHolder.itemView.getHeight() - icone.getIntrinsicHeight()) / 2;
        int icTopo = viewHolder.itemView.getTop() + (viewHolder.itemView.getHeight() - icone.getIntrinsicHeight()) / 2;
        int icInferior = icTopo + icone.getIntrinsicHeight();
        int icEsquerda = viewHolder.itemView.getLeft() + icMargem;
        int icDireita = icEsquerda + icone.getIntrinsicWidth();

        // define o icone do item
        icone.setBounds(icEsquerda, icTopo, icDireita, icInferior);
        icone.draw(c);
    }
}
