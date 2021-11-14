package br.com.ernanilima.jinventario.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.repository.UserRepository
import br.com.ernanilima.jinventario.data.network.google.Google
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import javax.inject.Inject

import br.com.ernanilima.jinventario.service.validation.ValidarEmailEnviado
import java.util.*

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val userDao: UserRepository,
    private var iFirebaseAuth: IFirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
): ViewModel(), ILogin.IViewModel {

    private val weakReference = WeakReference(context)

    private val _isInternet = MutableLiveData<Boolean>()
    val isInternet: LiveData<Boolean> = _isInternet

    private val _loginResult = MutableLiveData<IResultType>()
    val loginResult: LiveData<IResultType> = _loginResult

    private var _waitingTime: Long? = null
    val waitingTime get() = _waitingTime!!

    private var userEmail: String? = null

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAuth = FirebaseAuth(this)
    }

    /* Verifica se tem internet e realiza login */
    override fun login(userEmail: String, userPassword: String) {
        if (DeviceHelper.isInternet(weakReference.get())) {
            this.userEmail = userEmail
            iFirebaseAuth.loginUser(weakReference.get()!!, userEmail, userPassword)
        } else {
            _isInternet.postValue(false)
            _loginResult.postValue(ResultTypeFirebase.UNAUTHENTICATED_USER)
        }
    }

    /* Verifica se tem internet e realiza login com o gmail */
    override fun loginGmail() {
        if (DeviceHelper.isInternet(weakReference.get())) {
            Google.loginGmailUser(googleSignInClient, this)
        } else {
            _isInternet.postValue(false)
            _loginResult.postValue(ResultTypeFirebase.UNAUTHENTICATED_USER)
        }
    }

    override fun sendEmailVerification() {
        // verifica se cadastro ja existe
        val user = userDao.findByEmail(userEmail!!)

        // se data de envio for null, realiza envio do e-mail
        // se ja existir envio, verifica se pode enviar novamente
        if (user.dateSubmitVerification == null || ValidarEmailEnviado.isEnviarNovoEmail(user.dateSubmitVerification)) {
            iFirebaseAuth.sendEmailVerification(weakReference.get()!!) // envia e-mail
            user.dateSubmitVerification = Date(System.currentTimeMillis()) // envio com data/hora atual
            userDao.update(user)
        } else {
            // se o e-mail nao puder ser enviado
            _waitingTime = ValidarEmailEnviado.getTempoParaNovoEmail(user.dateSubmitVerification)
            setResult(ResultTypeLocal.WAIT_SEND_VERIFICATION)
        }
    }

    override fun setResult(iResult: IResultType) {
        when (iResult) {
            ResultTypeFirebase.LOGIN_DONE -> {
                if (userDao.findByEmail(iFirebaseAuth.getUserEmail()).deviceName.isNullOrBlank()) {
                    _loginResult.postValue(ResultTypeFirebase.FIRST_LOGIN_DONE)
                } else {
                    _loginResult.postValue(iResult)
                }
            }
            else -> {
                _loginResult.postValue(iResult)
            }
        }
    }
}