package br.com.ernanilima.jinventario.ui.camera

import androidx.fragment.app.FragmentManager

abstract class BaseCameraScanner constructor(supportFragmentManager: FragmentManager) {

    var positiveResultCallback: PositiveResultCallback? = null

    var type = Type.BASE

    val fragmentManager = supportFragmentManager

}