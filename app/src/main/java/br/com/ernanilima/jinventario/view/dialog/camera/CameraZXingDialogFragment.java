package br.com.ernanilima.jinventario.view.dialog.camera;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import br.com.ernanilima.jinventario.interfaces.IResultadoCameraScanner;

public class CameraZXingDialogFragment extends Fragment {

    private static CameraZXingDialogFragment CAMERA_ZXING;
    private Fragment fragment;
    private IResultadoCameraScanner iResultadoCameraScanner;

    private ActivityResultLauncher<Intent> abrirParaObterResultado;
    private IntentIntegrator integrator;

    public static CameraZXingDialogFragment getInstance() {
        if (CAMERA_ZXING == null) {
            CAMERA_ZXING = new CameraZXingDialogFragment();
        } return CAMERA_ZXING;
    }

    public void setFragment(Fragment fragment, IResultadoCameraScanner iResultadoCameraScanner) {
        this.fragment = fragment;
        this.iResultadoCameraScanner = iResultadoCameraScanner;
        construirExibirObterResultado();
    }

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

    public void criar() {
        integrator = new IntentIntegrator(fragment.getActivity());
        integrator.setPrompt("SCANNER");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        abrirParaObterResultado.launch(integrator.createScanIntent());
    }
}
