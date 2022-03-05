package br.com.ernanilima.jinventario.ui.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase.*
import br.com.ernanilima.jinventario.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val userDao: UserRepository,
    private var iFirebaseAuth: IFirebaseAuth
) : ViewModel(), ISplash.IViewModel {

    private val weakReference = WeakReference(context)

    private val geIsInternet: Boolean get() = DeviceHelper.isInternet(weakReference.get())
    private val _isInternet = MutableLiveData<Boolean>()
    val isInternet: LiveData<Boolean> = _isInternet

    private val _automaticLoginResult = MutableLiveData<IResultType>()
    val automaticLoginResult: LiveData<IResultType> = _automaticLoginResult

    private val _automaticLoginResultFirebaseError = MutableLiveData<String>()
    val automaticLoginResultFirebaseError: LiveData<String> = _automaticLoginResultFirebaseError

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAuth = FirebaseAuth(this)
    }

    /* Verifica se tem internet e se usuario esta autenticado */
    override fun checkAuthenticatedUserToLogin() {
        if (!geIsInternet) { setResult(UNAUTHENTICATED_USER); return}

        iFirebaseAuth.checkAuthenticatedUserToLogin()
    }

    /* Recebe o resultado da autenticacao */
    override fun setResult(iResult: IResultType) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500L)
            when (iResult) {
                AUTHENTICATED_USER -> {
                    if (userDao.findByEmail(iFirebaseAuth.getUserEmail()).deviceName.isNullOrBlank()) {
                        // se nome do aparelho for vazio para o usuario
                        _automaticLoginResult.postValue(FIRST_LOGIN_DONE)
                    } else {
                        _automaticLoginResult.postValue(iResult)
                    }
                }
                UNAUTHENTICATED_USER -> {
                    _automaticLoginResult.postValue(iResult)
                    // se o usuario nao estiver autenticado, atualiza o status da internet para a view
                    _isInternet.postValue(geIsInternet)
                }
                else -> {
                    _automaticLoginResult.postValue(iResult)
                }
            }
        }
    }

    override fun setResultFirebaseError(error: String) {
        _automaticLoginResultFirebaseError.postValue(error)
    }
}