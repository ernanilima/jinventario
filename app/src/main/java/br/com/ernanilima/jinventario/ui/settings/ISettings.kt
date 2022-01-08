package br.com.ernanilima.jinventario.ui.settings

interface ISettings {
    /** [SettingsViewModel] */
    interface IViewModel {
        /**
         * Salva as conmfiguracoes
         */
        fun saveSettings()
    }
}