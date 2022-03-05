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
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase.*
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import javax.inject.Inject

import br.com.ernanilima.jinventario.extension.common.WaitingTime
import br.com.ernanilima.jinventario.model.User
import java.util.*

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val userDao: UserRepository,
    private var iFirebaseAuth: IFirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
): ViewModel(), ILogin.IViewModel {

    private val weakReference = WeakReference(context)

    var user: User = User()

    private val geIsInternet: Boolean get() = DeviceHelper.isInternet(weakReference.get())
    private val _isInternet = MutableLiveData<Boolean>()
    val isInternet: LiveData<Boolean> = _isInternet

    private val _loginResult = MutableLiveData<IResultType>()
    val loginResult: LiveData<IResultType> = _loginResult

    private val _loginResultFirebaseError = MutableLiveData<String>()
    val loginResultFirebaseError: LiveData<String> = _loginResultFirebaseError

    private var _waitingTime: String? = null
    val waitingTime get() = _waitingTime!!

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAuth = FirebaseAuth(this)
    }

    /* Verifica se tem internet e realiza login */
    override fun login() {
        if (!geIsInternet) { _isInternet.postValue(false); return }

        iFirebaseAuth.loginUser(user.email, user.password)
    }

    /* Verifica se tem internet e realiza login com o gmail */
    override fun loginGmail() {
        if (!geIsInternet) { _isInternet.postValue(false); return }

        Google.loginGmailUser(googleSignInClient, this)
    }

    override fun sendEmailVerification() {
        if (!geIsInternet) { _isInternet.postValue(false); return }

        // verifica se cadastro ja existe
        val user = userDao.findByEmail(iFirebaseAuth.getUserEmail())

        // se data de envio for null, realiza envio do e-mail
        // se ja existir envio, verifica se pode enviar novamente
        if (user.dateSendingVerification == null || WaitingTime.get(user.dateSendingVerification) <= 0) {
            iFirebaseAuth.sendEmailVerification() // envia e-mail
            user.dateSendingVerification = Date(System.currentTimeMillis()) // envio com data/hora atual
            userDao.update(user)
        } else {
            // se o e-mail nao puder ser enviado
            _waitingTime = WaitingTime.get(user.dateSendingVerification).toString()
            setResult(ResultTypeLocal.WAIT_SEND_VERIFICATION)
        }
    }

    override fun setResult(iResult: IResultType) = when (iResult) {
        LOGIN_DONE -> {
            if (userDao.findByEmail(iFirebaseAuth.getUserEmail()).deviceName.isNullOrBlank()) {
                // se nome do aparelho for vazio para o usuario
                _loginResult.postValue(FIRST_LOGIN_DONE)
            } else {
                _loginResult.postValue(iResult)
            }
        }
        else -> {
            _loginResult.postValue(iResult)
        }
    }

    override fun setResultFirebaseError(errorCode: String) {
        _loginResultFirebaseError.postValue(errorCode)
    }
}