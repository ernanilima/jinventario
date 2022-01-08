package br.com.ernanilima.jinventario.ui.settings

import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDao: SettingsRepository
): ViewModel(), ISettings.IViewModel {

    init {
        this.popularSettings()
    }

    private fun popularSettings() {
        if (settingsDao.findSettings() != null) {
            println("COM CONFIGURACOES")
        } else {
            println("SEM CONFIGURACOES")
        }
    }
}