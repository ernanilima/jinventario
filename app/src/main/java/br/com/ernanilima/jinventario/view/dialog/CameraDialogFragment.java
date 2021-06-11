package br.com.ernanilima.jinventario.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IResultadoCameraScanner;
import br.com.ernanilima.jinventario.service.component.CameraScannerAnalyzer;

public class CameraDialogFragment extends DialogFragment {
    // https://developer.android.com/training/camerax/preview

    public static final String IRESULTADO_CAMERA = "ResultadoCameraScanner";
    private IResultadoCameraScanner iResultadoCameraScanner;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ExecutorService cameraExecutor;
    private PreviewView previewView;
    private CameraScannerAnalyzer cameraScannerAnalyzer;

    /** Construtor
     * @param iResultadoCameraScanner IResultadoCameraScanner - interface que obtera a resposta */
    public CameraDialogFragment(IResultadoCameraScanner iResultadoCameraScanner) {
        this.iResultadoCameraScanner = iResultadoCameraScanner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_camera_scanner, null);
        builder.setView(view)
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        // INICIALIZA
        previewView = view.findViewById(R.id.textureview);
        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());
        cameraScannerAnalyzer = new CameraScannerAnalyzer(this);
        cameraExecutor = Executors.newSingleThreadExecutor();

        cameraProviderFuture.addListener(this::criarCameraProvider, ContextCompat.getMainExecutor(getActivity()));

        return builder.create();
    }

    /** Verificar a disponibilidade do CameraProvider */
    private void criarCameraProvider() {
        try {
            ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
            criarCamera(cameraProvider);
        } catch (ExecutionException | InterruptedException e) {
            // Isso nunca deve ser alcançado.
        }
    }

    /** Selecionar uma câmera e vincular o ciclo de vida e casos de uso
     * @param cameraProvider ProcessCameraProvider */
    private void criarCamera(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageCapture imageCapture = new ImageCapture.Builder().build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1024, 768))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        imageAnalysis.setAnalyzer(cameraExecutor, cameraScannerAnalyzer);

        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);
    }

    /** Recebe o codigo de barras obtido na cameta
     * Fecha o dialog
     * @param codigo String - codigo de barras obtido pela camera */
    public void codigoColetado(String codigo) {
        iResultadoCameraScanner.setResultadoCameraScanner(codigo);
        dismiss();
    }
}
