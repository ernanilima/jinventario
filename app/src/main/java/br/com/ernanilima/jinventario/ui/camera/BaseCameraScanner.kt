package br.com.ernanilima.jinventario.ui.camera

import androidx.fragment.app.Fragment

/**
 * Base para exibir as cameras usadas
 * @param fragment Fragment - fragment da classe que esta abrindo a camera
 */
abstract class BaseCameraScanner(val fragment: Fragment) {

    /**
     * Codigo de barras retornado pela leitura da camera
     * Disponivel em [br.com.ernanilima.jinventario.ui.camera.CameraScanner] > [PositiveResultCallback]
     */
    var positiveResultCallback: PositiveResultCallback? = null

    /**
     * Tipo da classe
     * Disponivel em [br.com.ernanilima.jinventario.ui.camera.CameraScanner] > [Type]
     */
    var type = Type.BASE

}