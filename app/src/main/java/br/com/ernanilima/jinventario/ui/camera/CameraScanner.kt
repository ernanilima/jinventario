package br.com.ernanilima.jinventario.ui.camera

import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.UiThread
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import br.com.ernanilima.jinventario.databinding.DialogCameraScannerBinding
import br.com.ernanilima.jinventario.service.component.CameraScannerAnalyzer
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

enum class Type { BASE, MLKIT }
typealias PositiveResultCallback = (String) -> Unit

class CameraScanner constructor(
    private val baseCameraScanner: BaseCameraScanner
) : DialogFragment() {

    private var _bindingMLKit: DialogCameraScannerBinding? = null
    private val bindingMLKit get() = _bindingMLKit!!

    private var _cameraProvider: ListenableFuture<ProcessCameraProvider>? = null
    private val cameraProvider get() = _cameraProvider!!
    private var _cameraExecutor: ExecutorService? = null
    private val cameraExecutor get() = _cameraExecutor!!
    private var _cameraScannerAnalyzer: CameraScannerAnalyzer? = null
    private val cameraScannerAnalyzer get() = _cameraScannerAnalyzer!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return when (baseCameraScanner.type) {
            Type.MLKIT -> {
                _bindingMLKit = DialogCameraScannerBinding.inflate(inflater, container, false)
                setLayoutMLKit()
                bindingMLKit.root
            }
            else -> {
                super.onCreateView(inflater, container, savedInstanceState)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        this.dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    @UiThread
    private fun setLayoutMLKit() {
        // INICIALIZA
        _cameraProvider = ProcessCameraProvider.getInstance(requireActivity())
        _cameraExecutor = Executors.newSingleThreadExecutor()
        _cameraScannerAnalyzer = CameraScannerAnalyzer(this)

        // CONSTRUIR CAMERA
        cameraProvider.addListener({
            val preview = Preview.Builder().build()

            preview.setSurfaceProvider(bindingMLKit.textureview.surfaceProvider)

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            val imageCapture = ImageCapture.Builder().build()

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1024, 768))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(cameraExecutor, cameraScannerAnalyzer)

            cameraProvider.get().unbindAll()
            cameraProvider.get().bindToLifecycle(this, cameraSelector, preview,
                imageCapture, imageAnalysis)

        }, ContextCompat.getMainExecutor(activity))
    }

    fun barcode(barcode: String) {
        baseCameraScanner.positiveResultCallback?.invoke(barcode)
        dismiss()
    }

    fun close() {
        if (this.tag != null) dismiss()
    }

    fun show() {
        this.show(baseCameraScanner.fragmentManager, baseCameraScanner::class.java.simpleName)
    }
}