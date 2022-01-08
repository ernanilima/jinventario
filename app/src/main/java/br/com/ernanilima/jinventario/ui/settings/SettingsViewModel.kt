package br.com.ernanilima.jinventario.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.REFRESH_SETTINGS
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.SAVED_SETTINGS
import br.com.ernanilima.jinventario.model.Settings
import br.com.ernanilima.jinventario.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDao: SettingsRepository
): ViewModel(), ISettings.IViewModel {

    val settings: Settings = Settings()

    private val _settingsResult = MutableLiveData<IResultType>()
    val settingsResult: LiveData<IResultType> = _settingsResult

    init {
        this.popularSettings()
    }

    override fun saveSettings() {
        settingsDao.update(settings)
        _settingsResult.postValue(SAVED_SETTINGS)
    }

    private fun popularSettings() {
        val settings: Settings? = settingsDao.findSettings()
        if (settings != null) {
            this.settings.id = settings.id
            this.settings.showPrice = settings.showPrice
            this.settings.cameraScanner = settings.cameraScanner
            this.settings.cameraScannerMlkit = settings.cameraScannerMlkit
            this.settings.cameraScannerZxing = settings.cameraScannerZxing
            _settingsResult.postValue(REFRESH_SETTINGS)
        }
    }
}