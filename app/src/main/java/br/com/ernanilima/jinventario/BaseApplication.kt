package br.com.ernanilima.jinventario

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe que inicia o dagger hilt.
 * @see [link](https://developer.android.com/training/dependency-injection/hilt-android?hl=pt-br#application-class)
 * @see [link](https://medium.com/androiddevelopers/hilt-and-dagger-annotations-cheat-sheet-9adea070e495)
 */
@HiltAndroidApp
class BaseApplication: Application() { }