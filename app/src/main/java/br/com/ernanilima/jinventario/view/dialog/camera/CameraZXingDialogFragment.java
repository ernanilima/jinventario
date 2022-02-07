package br.com.ernanilima.jinventario.view.dialog.camera;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import br.com.ernanilima.jinventario.interfaces.IResultadoCameraScanner;

@Deprecated
public class CameraZXingDialogFragment extends DialogFragment {

    private static CameraZXingDialogFragment DIALOG_FRAGMENT;
    private Fragment fragment;
    private IResultadoCameraScanner iResultadoCameraScanner;

    private ActivityResultLauncher<Intent> abrirParaObterResultado;
    private IntentIntegrator integrator;

    /** Cria um dialog para camera
     * @return CameraZXingDialogFragment - instancia da camera */
    public static CameraZXingDialogFragment novoDialog() {
        DIALOG_FRAGMENT = new CameraZXingDialogFragment();
        return DIALOG_FRAGMENT;
    }

    /** Defina o fragmento
     * Exemplo CameraZXingDialogFragment.novoDialog().setFragment(this).exibir();
     * @param fragment Fragment - fragment da tela que vai usar essa classe
     * @return CameraZXingDialogFragment */
    public CameraZXingDialogFragment setFragment(Fragment fragment) {
        this.fragment = fragment;
        construirExibirObterResultado();
        return this;
    }

    /** Constroi um callback para exibir e obter o resultado
     * Substitui startActivityForResult e onActivityResult */
    private void construirExibirObterResultado() {
        // https://developer.android.com/training/basics/intents/result
        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
        abrirParaObterResultado = fragment.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    IntentResult resultado = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if (resultado != null) {
                        iResultadoCameraScanner.setResultadoCameraScanner(resultado.getContents());
                    }
                });
    }

    /** Usado antes do metodo {@link CameraZXingDialogFragment#exibir()}
     * Exemplo CameraZXingDialogFragment.novoDialog().setReceberResposta(this).exibir();
     * @param iResultadoCameraScanner IResultadoCameraScanner - onde o resultado sera exibido
     * @return CameraZXingDialogFragment */
    public CameraZXingDialogFragment setReceberResposta(IResultadoCameraScanner iResultadoCameraScanner) {
        this.iResultadoCameraScanner = iResultadoCameraScanner;
        return this;
    }

    /** Usado para exibir a camera */
    public void exibir() {
        integrator = new IntentIntegrator(fragment.getActivity());
        integrator.setPrompt("SCANNER");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        abrirParaObterResultado.launch(integrator.createScanIntent());
    }
}
