package br.com.ernanilima.jinventario.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import br.com.ernanilima.jinventario.NomeAparelhoActivity
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.interfaces.ICadastro
import br.com.ernanilima.jinventario.interfaces.IEsqueceuSenha
import br.com.ernanilima.jinventario.interfaces.IInicioApp
import br.com.ernanilima.jinventario.interfaces.INomeAparelho
import br.com.ernanilima.jinventario.presenter.CadastroPresenter
import br.com.ernanilima.jinventario.presenter.EsqueceuSenhaPresenter
import br.com.ernanilima.jinventario.presenter.InicioAppPresenter
import br.com.ernanilima.jinventario.presenter.NomeAparelhoPresenter
import br.com.ernanilima.jinventario.repository.UserRepository
import br.com.ernanilima.jinventario.repository.impl.UserRepositoryImpl
import br.com.ernanilima.jinventario.repository.orm.DaoMaster
import br.com.ernanilima.jinventario.repository.orm.DaoSession
import br.com.ernanilima.jinventario.view.CadastroFragment
import br.com.ernanilima.jinventario.view.EsqueceuSenhaFragment
import br.com.ernanilima.jinventario.view.InicioAppFragment
import dagger.Binds

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
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
    abstract fun bindCadastroFragment(fragment: CadastroFragment): ICadastro.IView

    @Binds
    abstract fun bindCadastroPresenter(presenter: CadastroPresenter): ICadastro.IPresenter

    @Binds
    abstract fun bindEsqueceuSenhaFragment(fragment: EsqueceuSenhaFragment): IEsqueceuSenha.IView

    @Binds
    abstract fun bindEsqueceuSenhaPresenter(presenter: EsqueceuSenhaPresenter): IEsqueceuSenha.IPresenter

    @Binds
    abstract fun bindInicioFragment(fragment: InicioAppFragment): IInicioApp.IView

    @Binds
    abstract fun bindInicioPresenter(presenter: InicioAppPresenter): IInicioApp.IPresenter

}

@InstallIn(FragmentComponent::class)
@Module
object FragmentModule {

    @Provides
    fun provideCadastroFragment(fragment: Fragment): CadastroFragment {
        return fragment as CadastroFragment
    }

    @Provides
    fun provideEsqueceuSenhaFragment(fragment: Fragment): EsqueceuSenhaFragment {
        return fragment as EsqueceuSenhaFragment
    }

    @Provides
    fun provideInicioFragment(fragment: Fragment): InicioAppFragment {
        return fragment as InicioAppFragment
    }

}


@InstallIn(ActivityComponent::class)
@Module
abstract class MVPActivityModule {

    @Binds
    abstract fun bindNomeAparelhoFragment(fragment: NomeAparelhoActivity): INomeAparelho.IView

    @Binds
    abstract fun bindNomeAparelhoPresenter(presenter: NomeAparelhoPresenter): INomeAparelho.IPresenter

}

@InstallIn(ActivityComponent::class)
@Module
object ActivityModule {

    @Provides
    fun provideNomeAparelhoActivity(activity: Activity): NomeAparelhoActivity {
        return activity as NomeAparelhoActivity
    }

}
