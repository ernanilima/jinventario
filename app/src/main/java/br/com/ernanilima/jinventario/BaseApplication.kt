package br.com.ernanilima.jinventario

import android.app.Application
import br.com.ernanilima.jinventario.repository.orm.DaoMaster
import br.com.ernanilima.jinventario.repository.orm.DaoSession
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.greendao.database.Database

/** Classe que inicia o dagger hilt.
 * @see [link](https://developer.android.com/training/dependency-injection/hilt-android?hl=pt-br#application-class)
 * @see [link](https://medium.com/androiddevelopers/hilt-and-dagger-annotations-cheat-sheet-9adea070e495) */
@HiltAndroidApp
class BaseApplication: Application() {
    // IMPORTANTE: DaoMaster, DaoSession e DAOs sao criados automaticamente
    // ao executar o projeto. Isso esta descriminado no proprio github.
    // https://github.com/greenrobot/greenDAO
    // !!TEMPORARIO!!, O GREENDAO SERA CRIADO EM UM MODULO

    private var daoSession: DaoSession? = null

    override fun onCreate() {
        super.onCreate()
        val helper: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(this, "jinventario-db")
        val db: Database = helper.getWritableDb()
        daoSession = DaoMaster(db).newSession()
    }

    fun getSessao(): DaoSession? {
        return daoSession
    }
}