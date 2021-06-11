package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IResultadoCameraScanner;
import br.com.ernanilima.jinventario.service.component.CameraScannerAnalyzer;

public class CameraScannerFragment extends Fragment {
    // https://developer.android.com/training/camerax/preview

    public static final String IRESULTADO_CAMERA = "ResultadoCameraScanner";

    private IResultadoCameraScanner iResultadoCameraScanner;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ExecutorService cameraExecutor;
    private PreviewView previewView;
    private CameraScannerAnalyzer cameraScannerAnalyzer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // argumento recebido de outro fragment, basicamente recebe a interface para obter a resposta da camera scanner
        getParentFragmentManager().setFragmentResultListener(this.getClass().getCanonicalName(), this,
                (requestKey, result) -> setInterfaceResultado((IResultadoCameraScanner) result.getSerializable(IRESULTADO_CAMERA)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_camera_scanner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // INICIALIZA
        previewView = view.findViewById(R.id.textureview);
        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());
        cameraScannerAnalyzer = new CameraScannerAnalyzer(this);
        cameraExecutor = Executors.newSingleThreadExecutor();

        cameraProviderFuture.addListener(this::criarCameraProvider, ContextCompat.getMainExecutor(getActivity()));
    }

    /** Recebe a interface de resultado
     * Interface usada para exibir o resultado da camera scanner
     * @param iResultadoCameraScanner IResultadoCameraScanner */
    private void setInterfaceResultado(IResultadoCameraScanner iResultadoCameraScanner) {
        this.iResultadoCameraScanner = iResultadoCameraScanner;
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
     * @param codigo String - codigo de barras obtido pela camera */
    public void codigoColetado(String codigo) {
        iResultadoCameraScanner.setResultadoCameraScanner(codigo);
        //((Activity) requireParentFragment().getContext()).onBackPressed();
    }
}
