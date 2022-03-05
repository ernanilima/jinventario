package br.com.ernanilima.jinventario.extension.common.dialog

import androidx.fragment.app.FragmentManager

abstract class BaseDialog constructor(supportFragmentManager: FragmentManager) {

    var title: String? = null
    var message: String? = null
    var barcode: String? = null
    var totalItems: String? = null
    var titleNegativeButton: String? = null
    var titlePositiveButton: String? = null
    var negativeButtonBold: Boolean? = null
    var positiveButtonBold: Boolean? = null

    var btnNegativeCallback: BtnNegativeCallback? = null
    var btnPositiveCallback: BtnPositiveCallback? = null

    var type = Type.BASE

    val fragmentManager = supportFragmentManager

}