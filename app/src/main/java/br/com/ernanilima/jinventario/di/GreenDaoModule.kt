package br.com.ernanilima.jinventario.di

import android.content.Context
import androidx.fragment.app.Fragment
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.interfaces.IInicioApp
import br.com.ernanilima.jinventario.presenter.InicioAppPresenter
import br.com.ernanilima.jinventario.repository.UserRepository
import br.com.ernanilima.jinventario.repository.impl.UserRepositoryImpl
import br.com.ernanilima.jinventario.repository.orm.DaoMaster
import br.com.ernanilima.jinventario.repository.orm.DaoSession
import br.com.ernanilima.jinventario.view.InicioAppFragment
import dagger.Binds

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.greenrobot.greendao.database.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GreenDaoModule {
    // IMPORTANTE: DaoMaster, DaoSession e DAOs sao criados automaticamente
    // ao executar o projeto. Isso esta descriminado no proprio github.
    // https://github.com/greenrobot/greenDAO

    @Provides
    @Singleton
    fun provideDaoSession(@ApplicationContext context: Context): DaoSession {
        val helper: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(context, "jinventario-db")
        val db: Database = helper.writableDb
        return DaoMaster(db).newSession()
    }

    @Provides
    @Singleton
    fun provideUserRepositoryImpl(daoSession: DaoSession): UserRepositoryImpl {
        return UserRepositoryImpl(daoSession.userDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository = userRepositoryImpl

}

@Module
@InstallIn(SingletonComponent::class)
abstract class UiModule {

    @Binds
    abstract fun bindFirebase(impl: FirebaseAuth): IFirebaseAuth

}

@InstallIn(FragmentComponent::class)
@Module
abstract class MVPModule {

    @Binds
    abstract fun bindInicioFragment(fragment: InicioAppFragment): IInicioApp.IView

    @Binds
    abstract fun bindInicioPresenter(presenter: InicioAppPresenter): IInicioApp.IPresenter

}

@InstallIn(FragmentComponent::class)
@Module
object FragmentModule {

    @Provides
    fun provideInicioFragment(fragment: Fragment): InicioAppFragment {
        return fragment as InicioAppFragment
    }

}