package br.com.ernanilima.jinventario.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IResultadoDialog;
import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;

public class AlteracaoDialogFragment extends DialogFragment {

    private ItemContagem mItemContagem;
    public static final String MODEL_ITEM_CONTAGEM = "AlterarItemContagem";

    private IResultadoDialog iResultadoDialog;
    private TextInputLayout campo_codbarras, campo_qtd_dcaixa, campo_qtd_pcaixa;
    public AppCompatButton btn_ok;

    /** Construtor
     * @param iResultadoDialog IResultadoDialog */
    public AlteracaoDialogFragment(IResultadoDialog iResultadoDialog) {
        this.iResultadoDialog = iResultadoDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemContagem = (ItemContagem) getArguments().getSerializable(MODEL_ITEM_CONTAGEM);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_contagem_inserir, null);
        builder.setView(view)
                .setTitle("Alteração")
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Confirmar", (dialog, which) -> confirmar());

        campo_codbarras = view.findViewById(R.id.campo_codbarras);
        campo_qtd_dcaixa = view.findViewById(R.id.campo_qtd_dcaixa);
        campo_qtd_pcaixa = view.findViewById(R.id.campo_qtd_pcaixa);
        btn_ok = view.findViewById(R.id.btn_ok);
        btn_ok.setVisibility(View.INVISIBLE);

        atualizarParaAlteracao();

        return builder.create();
    }

    /** Abre o Dialog com os dados do item que vai ser alterado */
    private void atualizarParaAlteracao() {
        campo_codbarras.getEditText().setText(mItemContagem.getCodigoBarras());
        campo_qtd_dcaixa.getEditText().setText(mItemContagem.getQtdDeCaixas());
        campo_qtd_pcaixa.getEditText().setText(mItemContagem.getQtdPorCaixa());
    }

    /** Confirma a alteracao realizada no dialog */
    private void confirmar() {
        if (validarCampos()) {
            mItemContagem.setCodigoBarras(campo_codbarras.getEditText().getText().toString());
            mItemContagem.setQtdDeCaixas(campo_qtd_dcaixa.getEditText().getText().toString());
            mItemContagem.setQtdPorCaixa(campo_qtd_pcaixa.getEditText().getText().toString());
            iResultadoDialog.setResultadoDialog(TipoResultado.CONFIRMAR_ALTERACAO, mItemContagem);
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(campo_codbarras, MensagensAlerta.CODBARRAS_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(campo_qtd_dcaixa, MensagensAlerta.QUANTIDADE_DE_CAIXA_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(campo_qtd_pcaixa, MensagensAlerta.QUANTIDADE_POR_CAIXA_INVALIDO.getMsg());
    }
}
