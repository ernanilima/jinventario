package br.com.ernanilima.jinventario.extension.common

import android.app.Activity
import br.com.ernanilima.jinventario.R
import com.google.android.material.textfield.TextInputLayout

class Validator {
    companion object {

        fun isEmpty(value: String?): Boolean {
            return value == null || value == "null" || value.trim() == "" || value.isEmpty()
        }

        fun showError(inputLayout: TextInputLayout, activity: Activity) {
            inputLayout.isErrorEnabled = true
            inputLayout.error = getErrorMessage(activity)
        }

        private fun getErrorMessage(activity: Activity): String {
            return activity.getString(R.string.ih_field_empty_error)
        }
    }
}