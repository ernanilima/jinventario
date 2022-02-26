package br.com.ernanilima.jinventario.service.component

import br.com.ernanilima.jinventario.ui.camera.CameraScanner
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import android.annotation.SuppressLint
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning

/**
 * @see (https://developers.google.com/ml-kit/vision/barcode-scanning/android)
 */
class CameraScannerAnalyzer(
    private val cameraScanner: CameraScanner
) : ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy) {
        createScanner(imageProxy)
    }

    /**
     * Cria o leitor de codigo de barras usado na camera
     * @param imageProxy ImageProxy - imagem da tela
     */
    private fun createScanner(imageProxy: ImageProxy) {
        @SuppressLint("UnsafeOptInUsageError")
        val mediaImage = imageProxy.image!!
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        // tipos de codigos aceitos
        val scannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_EAN_8
            ).build()
        val scanner = BarcodeScanning.getClient(scannerOptions)

        // cria ouvintes
        scanner.process(image)
            .addOnSuccessListener {
                barcodes: List<Barcode> -> leitorCodigoBarras(barcodes)
            }.addOnCompleteListener {
                imageProxy.close()
            }
    }

    /**
     * Caso o ouvinte apresente sucesso nos tipos informados
     * esse classe filtra o tipo recebido
     * @param barcodes List<Barcode> - lista de codigos
    */
    private fun leitorCodigoBarras(barcodes: List<Barcode>) {
        for (barcode in barcodes) {
            if (barcode.valueType == Barcode.TYPE_PRODUCT) {
                val code = barcode.rawValue
                if (code != null)
                    cameraScanner.barcode(code)
            }
        }
    }
}
