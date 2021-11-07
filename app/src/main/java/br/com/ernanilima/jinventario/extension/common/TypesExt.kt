package br.com.ernanilima.jinventario.extension.common

fun Boolean.ifTrue(function: (Boolean) -> Unit): Boolean {
    if (this) { function.invoke(this) }
    return this
}

fun Boolean.ifFalse(function: (Boolean) -> Unit): Boolean {
    if (!this) { function.invoke(this) }
    return this
}