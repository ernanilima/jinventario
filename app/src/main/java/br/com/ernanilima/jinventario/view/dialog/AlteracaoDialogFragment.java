package br.com.ernanilima.jinventario.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IResultadoCameraScanner;
import br.com.ernanilima.jinventario.interfaces.IResultadoDialog;
import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;

public class AlteracaoDialogFragment extends DialogFragment implements IResultadoCameraScanner {

    private ItemContagem mItemContagem;
    public static final String MODEL_ITEM_CONTAGEM = "AlterarItemContagem";
    public static final String CAMERA_SCANNER = "UsarCameraComoScanner";
    private Boolean cameraScanner;

    private IResultadoDialog iResultadoDialog;
    private AlertDialog.Builder builder;
    private ActivityResultLauncher<Intent> abrirParaObterResultado;
    private IntentIntegrator integrator;
    private TextInputLayout campo_codbarras, campo_qtd_dcaixa, campo_qtd_pcaixa;
    public AppCompatButton btn_ok;
    private AppCompatImageButton btn_camera_scanner;

    /** Construtor
     * @param iResultadoDialog IResultadoDialog */
    public AlteracaoDialogFragment(IResultadoDialog iResultadoDialog) {
        this.iResultadoDialog = iResultadoDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemContagem = (ItemContagem) getArguments().getSerializable(MODEL_ITEM_CONTAGEM);
        cameraScanner = getArguments().getBoolean(CAMERA_SCANNER);
        construirExibirObterResultado();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_contagem_inserir, null);
        builder.setView(view)
                .setTitle("Alteração")
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Confirmar", null);

        campo_codbarras = view.findViewById(R.id.campo_codbarras);
        campo_qtd_dcaixa = view.findViewById(R.id.campo_qtd_dcaixa);
        campo_qtd_pcaixa = view.findViewById(R.id.campo_qtd_pcaixa);
        btn_camera_scanner = view.findViewById(R.id.btn_camerascanner);
        btn_ok = view.findViewById(R.id.btn_ok);

        btn_camera_scanner.setVisibility(View.INVISIBLE);
        btn_ok.setVisibility(View.INVISIBLE);

        // largura do botao, nao pode ser 0(zero)
        btn_camera_scanner.getLayoutParams().width = 1;
        btn_ok.getLayoutParams().width = 1;

        // margem do botao
        ViewGroup.MarginLayoutParams paramsCameraScanner = (ViewGroup.MarginLayoutParams) btn_camera_scanner.getLayoutParams();
        paramsCameraScanner.leftMargin = 0; paramsCameraScanner.rightMargin = 0;
        ViewGroup.MarginLayoutParams paramsOk = (ViewGroup.MarginLayoutParams) btn_ok.getLayoutParams();
        paramsOk.leftMargin = 0; paramsOk.rightMargin = 0;

        validarConfiguracao();
        atualizarParaAlteracao();

        return builder.create();
    }

    private void validarConfiguracao() {
        if (cameraScanner) { // se na configuracao o uso for habilitado, exibe o botao de usar camera como scanner
            builder.setNeutralButton("Camera Código Barras", null);
        }
    }

    private void construirExibirObterResultado() {
        // https://developer.android.com/training/basics/intents/result
        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
        abrirParaObterResultado = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    IntentResult resultado = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if (resultado != null) {
                        setResultadoCameraScanner(resultado.getContents());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if(alertDialog != null) {
            // botao neutro, abre a camera scanner
            Button botaoNeutro = alertDialog.getButton(Dialog.BUTTON_NEUTRAL);
            botaoNeutro.setOnClickListener(v -> abrirCameraScanner());
            // botao positivo
            Button botaoConfirmar = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            botaoConfirmar.setOnClickListener(v -> confirmar());
        }
    }

    /** Abre a camera scanner
     * Envia esse dialog para obter a resposta da camera */
    private void abrirCameraScanner() {
        integrator = new IntentIntegrator(getActivity());
        integrator.setPrompt("SCANNER");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        abrirParaObterResultado.launch(integrator.createScanIntent());

//        CameraDialogFragment dCameraFragment = new CameraDialogFragment(this);
//        Bundle argumento = new Bundle();
//        // armazena a interface como argumento para que possa ser receptado pelo dialog de scanner com a canera
//        argumento.putSerializable(CameraDialogFragment.IRESULTADO_CAMERA, this);
//        dCameraFragment.setArguments(argumento);
//        dCameraFragment.setCancelable(false);
//        dCameraFragment.show(getActivity().getSupportFragmentManager(), "tag");
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
            dismiss(); // fecha o dialog
        }
    }

    private boolean validarCampos() {
        return ValidarCampo.vazio(campo_codbarras, MensagensAlerta.CODBARRAS_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(campo_qtd_dcaixa, MensagensAlerta.QUANTIDADE_DE_CAIXA_INVALIDO.getMsg()) &&
                ValidarCampo.vazio(campo_qtd_pcaixa, MensagensAlerta.QUANTIDADE_POR_CAIXA_INVALIDO.getMsg());
    }

    @Override
    /** Resultado recebido da camera scanner */
    public void setResultadoCameraScanner(String codigoBarras) {
        campo_codbarras.getEditText().setText(codigoBarras);
        campo_qtd_dcaixa.requestFocus();
    }
}
