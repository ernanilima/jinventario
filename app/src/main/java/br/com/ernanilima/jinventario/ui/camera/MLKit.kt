package br.com.ernanilima.jinventario.ui.camera

import androidx.fragment.app.Fragment

class MLKit constructor(fragment: Fragment) : BaseCameraScanner(fragment) {

    init {
        type = Type.MLKIT
    }

    /**
     * Recebe o codigo enviado para o metodo [CameraScanner.barcode]
     */
    fun setPositiveResult(positiveResultCallback: PositiveResultCallback): MLKit {
        super.positiveResultCallback = positiveResultCallback
        return this
    }
}
