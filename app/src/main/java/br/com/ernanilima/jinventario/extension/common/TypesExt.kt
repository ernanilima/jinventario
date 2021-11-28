package br.com.ernanilima.jinventario.extension.common

import android.database.Cursor
import java.util.*

fun Boolean.ifTrue(function: (Boolean) -> Unit): Boolean {
    if (this) { function.invoke(this) }
    return this
}

fun Boolean.ifFalse(function: (Boolean) -> Unit): Boolean {
    if (!this) { function.invoke(this) }
    return this
}

fun Cursor.getDate(value: Long?): Date {
    if (value != null) {
        return Date(value)
    }
    return Date(0) // Wed Dec 31 21:00:00 GMT-03:00 1969
}