package br.com.ernanilima.jinventario.ui.camera

import androidx.fragment.app.Fragment

abstract class BaseCameraScanner(val fragment: Fragment) {

    var positiveResultCallback: PositiveResultCallback? = null

    var type = Type.BASE

}