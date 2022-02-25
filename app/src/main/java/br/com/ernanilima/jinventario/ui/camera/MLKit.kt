package br.com.ernanilima.jinventario.ui.camera

import androidx.fragment.app.FragmentManager

class MLKit constructor(supportFragmentManager: FragmentManager) : BaseCameraScanner(supportFragmentManager) {

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
