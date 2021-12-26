package br.com.ernanilima.jinventario.repository.impl

import br.com.ernanilima.jinventario.model.Settings
import br.com.ernanilima.jinventario.repository.SettingsRepository
import br.com.ernanilima.jinventario.repository.orm.SettingsDao
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val dao: SettingsDao): SettingsRepository {

    /**
     * Atualiza um configuracoes
     * @param settings Settings - configuracoes para atualizar
     */
    override fun update(settings: Settings) {
        settings.id = findSettings().id
        dao.save(settings)
    }

    /**
     * @return configuracao principal
     */
    private fun findSettings(): Settings {
        return dao.load(1L) ?: Settings()
    }
}