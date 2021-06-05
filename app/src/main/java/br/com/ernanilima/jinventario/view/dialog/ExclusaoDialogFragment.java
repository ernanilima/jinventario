package br.com.ernanilima.jinventario.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import br.com.ernanilima.jinventario.interfaces.IResultadoDialog;
import br.com.ernanilima.jinventario.model.IModel;

public class ExclusaoDialogFragment extends DialogFragment {

    private IModel iModel;
    public static final String MODEL_ITEM_CONTAGEM = "ExcluirItemContagem";

    private IResultadoDialog iResultadoDialog;

    /** Construtor
     * @param iResultadoDialog IResultadoDialog */
    public ExclusaoDialogFragment(IResultadoDialog iResultadoDialog) {
        this.iResultadoDialog = iResultadoDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iModel = (IModel) getArguments().getSerializable(MODEL_ITEM_CONTAGEM);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Exclusão");
        builder.setMessage("Você tem certeza que deseja excluir o item de número " + (iModel.getPosicaoItem()+1) + "?");
        builder.setNegativeButton("Cancelar", (dialog, which) -> cancelar());
        builder.setPositiveButton("Confirmar", (dialog, which) -> confirmar());

        return builder.create();
    }

    /** Cancela a exclusao */
    private void cancelar() {
        iResultadoDialog.resultadoItemExcluidoDialog(false, iModel);
    }

    /** Confirma a exclusao realizada no dialog */
    private void confirmar() {
        iResultadoDialog.resultadoItemExcluidoDialog(true, iModel);
    }
}
