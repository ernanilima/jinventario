package br.com.ernanilima.jinventario.view.dialog.camera;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IResultadoCameraScanner;
import br.com.ernanilima.jinventario.service.component.CameraScannerAnalyzer;

public class CameraMLKitDialogFragment extends DialogFragment {
    // https://developer.android.com/training/camerax/preview

    private static CameraMLKitDialogFragment DIALOG_FRAGMENT;

    private IResultadoCameraScanner iResultadoCameraScanner;
    private FragmentManager fragmentManager;
    private AlertDialog.Builder aDialog;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ExecutorService cameraExecutor;
    private PreviewView previewView;
    private CameraScannerAnalyzer cameraScannerAnalyzer;

    /** Cria um dialog para camera
     * @return CameraMLKitDialogFragment - nova instancia do dialog */
    public static CameraMLKitDialogFragment novoDialog() {
        DIALOG_FRAGMENT = new CameraMLKitDialogFragment();
        DIALOG_FRAGMENT.setCancelable(false);
        return DIALOG_FRAGMENT;
    }

    /** Usado antes do metodo {@link CameraMLKitDialogFragment#exibir()}
     * Exemplo CameraMLKitDialogFragment.novoDialog().setFragmentManager(getParentFragmentManager()).exibir();
     * @param fragmentManager FragmentManager - tela que o dialog sera exibido
     * @return CameraMLKitDialogFragment */
    public CameraMLKitDialogFragment setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    /** Usado antes do metodo {@link CameraMLKitDialogFragment#exibir()}
     * Exemplo CameraMLKitDialogFragment.novoDialog().setReceberResposta(this).exibir();
     * @param iResultadoCameraScanner IResultadoCameraScanner - onde o resultado sera exibido
     * @return CameraMLKitDialogFragment */
    public CameraMLKitDialogFragment setReceberResposta(IResultadoCameraScanner iResultadoCameraScanner) {
        this.iResultadoCameraScanner = iResultadoCameraScanner;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // cria o dialog
        aDialog = new AlertDialog.Builder(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_camera_scanner, null);

        aDialog.setView(view)
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        // INICIALIZA
        previewView = view.findViewById(R.id.textureview);
        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());
        cameraScannerAnalyzer = new CameraScannerAnalyzer(this);
        cameraExecutor = Executors.newSingleThreadExecutor();

        return aDialog.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        cameraProviderFuture.addListener(this::criarCameraProvider, ContextCompat.getMainExecutor(getActivity()));
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

    /** Selecionar uma câmera e vincula o ciclo de vida e casos de uso
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

    /** Usado para exibir a camera */
    public void exibir() {
        DIALOG_FRAGMENT.show(fragmentManager, "camera_mlkit");
    }

    /** Recebe o codigo de barras obtido pela camera
     * Fecha o dialog
     * @param codigo String - codigo de barras obtido pela camera */
    public void codigoColetado(String codigo) {
        iResultadoCameraScanner.setResultadoCameraScanner(codigo);
        dismiss();
    }
}
