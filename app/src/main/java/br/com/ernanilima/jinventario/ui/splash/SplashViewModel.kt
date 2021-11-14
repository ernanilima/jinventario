package br.com.ernanilima.jinventario.ui.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase
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
    private var iFirebaseAuth: IFirebaseAuth
) : ViewModel(), ISplash.IViewModel {

    private val weakReference = WeakReference(context)

    private val _isInternet = MutableLiveData<Boolean>()
    val isInternet: LiveData<Boolean> = _isInternet

    private val _automaticLoginResult = MutableLiveData<IResultType>()
    val automaticLoginResult: LiveData<IResultType> = _automaticLoginResult

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAuth = FirebaseAuth(this)
    }

    /* Verifica se tem internet e se usuario esta autenticado */
    override fun checkAuthenticatedUserToLogin() {
        if (DeviceHelper.isInternet(weakReference.get())) {
            iFirebaseAuth.checkAuthenticatedUserToLogin()
        } else {
            setResult(ResultTypeFirebase.UNAUTHENTICATED_USER)
        }
    }

    /* Recebe o resultado da autenticacao */
    override fun setResult(iResult: IResultType) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            when (iResult) {
                ResultTypeFirebase.UNAUTHENTICATED_USER -> {
                    _automaticLoginResult.postValue(iResult)
                    // se o usuario nao estiver autenticado, atualiza o status da internet para a view
                    _isInternet.postValue(DeviceHelper.isInternet(weakReference.get()))
                }
                else -> {
                    _automaticLoginResult.postValue(iResult)
                }
            }
        }
    }
}