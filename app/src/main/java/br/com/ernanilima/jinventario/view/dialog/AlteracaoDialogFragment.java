package br.com.ernanilima.jinventario.view.dialog;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IResultadoCameraScanner;
import br.com.ernanilima.jinventario.interfaces.IResultadoDialog;
import br.com.ernanilima.jinventario.interfaces.IResultadoPermissao;
import br.com.ernanilima.jinventario.model.Configuracao;
import br.com.ernanilima.jinventario.model.ItemContagem;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.service.validation.ValidarCampo;
import br.com.ernanilima.jinventario.service.validation.ValidarPermissoes;
import br.com.ernanilima.jinventario.view.dialog.camera.CameraMLKitDialogFragment;
import br.com.ernanilima.jinventario.view.dialog.camera.CameraZXingDialogFragment;
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom;

public class AlteracaoDialogFragment extends DialogFragment implements IResultadoCameraScanner, IResultadoPermissao {

    private static String[] permissoes = new String[] {
            Manifest.permission.CAMERA
    };

    private static AlteracaoDialogFragment DIALOG_FRAGMENT;
    public static final String MODEL_ITEM_CONTAGEM = "AlterarItemContagem";
    public static final String MODEL_CONFIGURACAO = "UsarCameraComoScanner";
    private ItemContagem mItemContagem;
    private Configuracao mConfiguracao;

    private IResultadoDialog iResultadoDialog;
    private AlertDialog.Builder aDialog;

    private ValidarPermissoes vPermissoes;
    private CameraZXingDialogFragment dfCameraZXing;
    private TextInputLayout campo_codbarras, campo_qtd_dcaixa, campo_qtd_pcaixa;
    public AppCompatButton btn_ok;
    private AppCompatImageButton btn_camera_scanner;

    /** Cria um dialog de alteracao
     * @param iResultadoDialog IResultadoDialog - onde o resultado sera exibido
     * @param argumentos Bundle - argumentos para o dialog
     * @return AlteracaoDialogFragment - nova instancia do dialog */
    public static AlteracaoDialogFragment novoDialog(IResultadoDialog iResultadoDialog, Bundle argumentos) {
        DIALOG_FRAGMENT = new AlteracaoDialogFragment(iResultadoDialog);
        DIALOG_FRAGMENT.setArguments(argumentos);
        DIALOG_FRAGMENT.setCancelable(false);
        return DIALOG_FRAGMENT;
    }

    /** Construtor
     * @param iResultadoDialog IResultadoDialog - onde o resultado sera exibido */
    private AlteracaoDialogFragment(IResultadoDialog iResultadoDialog) {
        this.iResultadoDialog = iResultadoDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // cria o dialog
        aDialog = new AlertDialog.Builder(getActivity());

        // argumentos recebidos
        mItemContagem = (ItemContagem) getArguments().getSerializable(MODEL_ITEM_CONTAGEM);
        mConfiguracao = (Configuracao) getArguments().getSerializable(MODEL_CONFIGURACAO);

        // CLASSE PARA VALIDAR PERMISSOES
        vPermissoes = ValidarPermissoes.novaValidacao().setFragment(this).setPermissoes(permissoes);

        // CAMERA TIPO ZXING
        dfCameraZXing = CameraZXingDialogFragment.novoDialog().setFragment(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_contagem_inserir, null);

        aDialog.setView(view)
                .setTitle("Alteração")
                // ira verificar se o botao neutro sera exibido
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel())
                // botao confirmar implementado em outro metodo
                // dessa forma o botao nao fecha o dialog sem fazer as validados de campo
                .setPositiveButton("Confirmar", null);

        //INICIALIZA
        campo_codbarras = view.findViewById(R.id.campo_codbarras);
        campo_qtd_dcaixa = view.findViewById(R.id.campo_qtd_dcaixa);
        campo_qtd_pcaixa = view.findViewById(R.id.campo_qtd_pcaixa);
        btn_camera_scanner = view.findViewById(R.id.btn_camerascanner);
        btn_ok = view.findViewById(R.id.btn_ok);

        validarConfigUsarCamera();

        return aDialog.create();
    }

    private void validarConfigUsarCamera() {
        // verifica se a camera pode ser usada como scanner
        // por padrao a camera eh exibida mesmo que o usuario nunca tenha gravado nenhuma configuracao
        if (mConfiguracao == null || mConfiguracao.getCameraScanner()) {
            aDialog.setNeutralButton("Camera Como Scanner", null);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // botao invisivel
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

        // exibe os dados que devem ser editados
        campo_codbarras.getEditText().setText(mItemContagem.getCodigoBarras());
        campo_qtd_dcaixa.getEditText().setText(mItemContagem.getQtdDeCaixas());
        campo_qtd_pcaixa.getEditText().setText(mItemContagem.getQtdPorCaixa());

        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if(alertDialog != null) {
            // botao neutro, abre a camera scanner
            Button botaoNeutro = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
            botaoNeutro.setOnClickListener(v -> vPermissoes.setReceberResposta(this).validarPermissoes());

            // botao positivo
            Button botaoConfirmar = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            botaoConfirmar.setOnClickListener(v -> confirmar());
        }
    }

    /** Exibe o dialog
     * @param fragmentManager FragmentManager - tela que o dialog sera exibido */
    public void show(FragmentManager fragmentManager) {
        DIALOG_FRAGMENT.show(fragmentManager, "alterar_item");
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

    @Override
    /** Resultado recebido da solicitacao de permissao */
    public void setResultadoPermissao(boolean usarCameraComoScanner) {
        if (usarCameraComoScanner) { // se o aplicativo tiver a permissao de usar a camera

            // abre a camera scanner
            // envia esse dialog para obter a resposta da camera
            if (mConfiguracao == null || mConfiguracao.getCameraScannerMlkit()) {
                // por padrao essa eh a camera usada mesmo que o
                // usuario nunca tenha gravado nenhuma configuracao

                CameraMLKitDialogFragment.novoDialog()
                        .setFragmentManager(getParentFragmentManager())
                        .setReceberResposta(this)
                        .exibir();

            } else if (mConfiguracao != null && mConfiguracao.getCameraScannerZxing()) {
                // para usar essa camera, o usuario precisa escolher nas configuracoes
                dfCameraZXing.setReceberResposta(this).exibir();
            }
        } else { // se o aplicativo nao tiver a permissao de usar a camera
            SnackbarCustom.INSTANCE.warning(requireContext(), MensagensAlerta.SEM_PERMISSAO_CAMERA.getMsg());
        }
    }
}
