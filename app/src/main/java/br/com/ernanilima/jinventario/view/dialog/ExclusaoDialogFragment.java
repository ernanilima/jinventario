package br.com.ernanilima.jinventario.view.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import br.com.ernanilima.jinventario.interfaces.IResultadoDialog;
import br.com.ernanilima.jinventario.model.IModel;

public class ExclusaoDialogFragment extends DialogFragment {

    private static ExclusaoDialogFragment DIALOG_FRAGMENT;
    public static final String MODEL_ITEM_CONTAGEM = "ExcluirItemContagem";
    private IModel iModel;

    private IResultadoDialog iResultadoDialog;
    private androidx.appcompat.app.AlertDialog.Builder aDialog;

    /** Cria um dialog de exclusao
     * @param iResultadoDialog IResultadoDialog - onde o resultado sera exibido
     * @param argumento Bundle - argumento para o dialog
     * @return ExclusaoDialogFragment - nova instancia do dialog */
    public static ExclusaoDialogFragment novoDialog(IResultadoDialog iResultadoDialog, Bundle argumento) {
        DIALOG_FRAGMENT = new ExclusaoDialogFragment(iResultadoDialog);
        DIALOG_FRAGMENT.setArguments(argumento);
        DIALOG_FRAGMENT.setCancelable(false);
        return DIALOG_FRAGMENT;
    }

    /** Construtor
     * @param iResultadoDialog IResultadoDialog - onde o resultado sera exibido */
    private ExclusaoDialogFragment(IResultadoDialog iResultadoDialog) {
        this.iResultadoDialog = iResultadoDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // cria o dialog
        aDialog = new AlertDialog.Builder(getActivity());

        // argumento recebido
        iModel = (IModel) getArguments().getSerializable(MODEL_ITEM_CONTAGEM);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        aDialog.setTitle("Exclusão");
        aDialog.setMessage("Você tem certeza que deseja excluir o item de número " + (iModel.getPosicaoItem()+1) + "?")
                .setNegativeButton("Cancelar", (dialog, which) -> cancelar())
                .setPositiveButton("Confirmar", (dialog, which) -> confirmar());

        return aDialog.create();
    }

    /** Exibe o dialog
     * @param fragmentManager FragmentManager - tela que o dialog sera exibido */
    public void show(FragmentManager fragmentManager) {
        DIALOG_FRAGMENT.show(fragmentManager, "excluir_item");
    }

    /** Cancela a exclusao */
    private void cancelar() {
        iResultadoDialog.setResultadoDialog(TipoResultado.CANCELAR_EXCLUSAO, iModel);
    }

    /** Confirma a exclusao realizada no dialog */
    private void confirmar() {
        iResultadoDialog.setResultadoDialog(TipoResultado.CONFIRMAR_EXCLUSAO, iModel);
    }
}
