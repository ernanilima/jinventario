package br.com.ernanilima.jinventario.ui.camera

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator

class ZXing constructor(fragment: Fragment) : BaseCameraScanner(fragment) {

    private var _integrator: IntentIntegrator? = null
    private val integrator get() = _integrator!!
    private var _resultLauncher: ActivityResultLauncher<Intent>? = null
    private val resultLauncher get() = _resultLauncher!!

    init {
        type = Type.ZXING
        _resultLauncher = fragment.registerForActivityResult(StartActivityForResult()) {
                result: ActivityResult ->
            val resultIntegrator = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
            if (resultIntegrator != null && resultIntegrator.contents != null) {
                super.positiveResultCallback?.invoke(resultIntegrator.contents)
            }
        }
        _integrator = IntentIntegrator(fragment.activity)
    }

    fun show() {
        integrator.setBeepEnabled(true)
        integrator.setOrientationLocked(true)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES)
        resultLauncher.launch(integrator.createScanIntent())
    }

    /**
     * Recebe o codigo enviado para o metodo [CameraScanner.barcode]
     */
    fun setPositiveResult(positiveResultCallback: PositiveResultCallback): ZXing {
        super.positiveResultCallback = positiveResultCallback
        return this
    }
}
