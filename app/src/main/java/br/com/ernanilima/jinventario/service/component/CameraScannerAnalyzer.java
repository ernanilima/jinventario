package br.com.ernanilima.jinventario.service.component;

import android.annotation.SuppressLint;
import android.media.Image;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.ernanilima.jinventario.view.dialog.CameraDialogFragment;

public class CameraScannerAnalyzer implements ImageAnalysis.Analyzer {
    // https://developers.google.com/ml-kit/vision/barcode-scanning/android

    private final CameraDialogFragment cameraDialogFragment;

    /** Construtor
     * @param cameraDialogFragment CameraDialogFragment - dialog da camera scanner */
    public CameraScannerAnalyzer(CameraDialogFragment cameraDialogFragment) {
        this.cameraDialogFragment = cameraDialogFragment;
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        criarScanner(image);
    }

    /** Cria o leitor de codigo de barras usado na camera
     * @param imageProxy ImageProxy - imagem da tela */
    private void criarScanner(@NotNull ImageProxy imageProxy) {
        @SuppressLint("UnsafeOptInUsageError")
        Image image = imageProxy.getImage();

        // evita que o atributo seja nulo
        assert image != null;

        InputImage inputImage = InputImage.fromMediaImage(image, imageProxy.getImageInfo().getRotationDegrees());

        // tipos de codigos aceitos
        BarcodeScannerOptions scannerOptions = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_EAN_13, Barcode.FORMAT_EAN_8).build();

        BarcodeScanner scanner = BarcodeScanning.getClient(scannerOptions);

        // cria ouvintes
        scanner.process(inputImage)
                .addOnSuccessListener(this::leitorCodigoBarras)
                .addOnCompleteListener(task -> imageProxy.close());
    }


    /** Caso o ouvinte apresente sucesso nos tipos informados
     * esse classe filtra o tipo recebido
     * @param barcodes List<Barcode> - lista de codigos */
    private void leitorCodigoBarras(@NotNull List<Barcode> barcodes) {
        for (Barcode barcode : barcodes) {
            if (barcode.getValueType() == Barcode.TYPE_PRODUCT) {
                String codigo = barcode.getRawValue();
                cameraDialogFragment.codigoColetado(codigo);
            }
        }
    }
}
