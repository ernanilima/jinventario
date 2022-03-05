package br.com.ernanilima.jinventario.ui.forgotPassword

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase.NEW_PASSWORD_EMAIL_SENT
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.extension.common.WaitingTime
import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val userDao: UserRepository,
    private var iFirebaseAuth: IFirebaseAuth
): ViewModel(), IForgotPassword.IViewModel {

    private val weakReference = WeakReference(context)

    var user: User = User()

    private val geIsInternet: Boolean get() = DeviceHelper.isInternet(weakReference.get())
    private val _isInternet = MutableLiveData<Boolean>()
    val isInternet: LiveData<Boolean> = _isInternet

    private val _forgotPasswordResult = MutableLiveData<IResultType>()
    val forgotPasswordResult: LiveData<IResultType> = _forgotPasswordResult

    private val _forgotPasswordResultFirebaseError = MutableLiveData<String>()
    val forgotPasswordResultFirebaseError: LiveData<String> = _forgotPasswordResultFirebaseError

    private var _waitingTime: String? = null
    val waitingTime get() = _waitingTime!!

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAuth = FirebaseAuth(this)
    }

    /* Verifica se tem internet e realiza o envio de uma nova senha */
    override fun sendNewPassword() {
        if (!geIsInternet) { _isInternet.postValue(false); return }

        // verifica se cadastro ja existe
        user = userDao.findByEmail(user.email)

        // se data de envio for null, realiza envio do e-mail
        // se ja existir envio, verifica se pode enviar novamente
        if (user.dateSendingPassword == null || WaitingTime.get(user.dateSendingPassword) <= 0) {
            iFirebaseAuth.sendEmailForgotPassword(user.email) // envia um e-mail
        } else {
            // se o e-mail nao puder ser enviado
            _waitingTime = WaitingTime.get(user.dateSendingPassword).toString()
            setResult(ResultTypeLocal.WAIT_SEND_PASSWORD)
        }
    }

    override fun setResult(iResult: IResultType) = when (iResult) {
        NEW_PASSWORD_EMAIL_SENT -> {
            user.dateSendingPassword = Date(System.currentTimeMillis()) // envio com data/hora atual
            userDao.update(user)
            _forgotPasswordResult.postValue(iResult)
        }
        else -> {
            _forgotPasswordResult.postValue(iResult)
        }
    }

    override fun setResultFirebaseError(errorCode: String) {
        _forgotPasswordResultFirebaseError.postValue(errorCode)
    }
}