package br.com.ernanilima.jinventario.repository

import br.com.ernanilima.jinventario.model.Settings

interface SettingsRepository {
    fun update(settings: Settings)
}