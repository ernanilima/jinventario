package br.com.ernanilima.jinventario.ui.register

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val userDao: UserRepository,
    private var iFirebaseAuth: IFirebaseAuth
) : ViewModel(), IRegister.IViewModel {

    private val weakReference = WeakReference(context)

    var user: User = User()

    private val _isInternet = MutableLiveData<Boolean>()
    val isInternet: LiveData<Boolean> = _isInternet

    private val _registerResult = MutableLiveData<IResultType>()
    val registerResult: LiveData<IResultType> = _registerResult

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAuth = FirebaseAuth(this)
    }

    /* Verifica se tem internet e realiza o cadastro do usuario */
    override fun register() {
        if (DeviceHelper.isInternet(weakReference.get())) {
            iFirebaseAuth.registerUser(weakReference.get()!!, user.email, user.password)
        } else {
            _isInternet.postValue(false)
            _registerResult.postValue(ResultTypeFirebase.REGISTRATION_NOT_DONE)
        }
    }

    /**
     * Grava no banco de dados local o usuario com a data/hora do envio do e-mail
     */
    private fun verificationEmailSent() {
        val user = userDao.findByEmail(iFirebaseAuth.getUserEmail())
        user.dateSendingVerification = Date(System.currentTimeMillis()) // envio com data/hora atual
        user.dateSendingPassword = Date(System.currentTimeMillis()) // envio com data/hora atual
        userDao.insert(user)
    }

    /**
     * Resultado recebido do firebase
     * @param iResult IResultType - tipo de resultado
     */
    override fun setResult(iResult: IResultType) {
        when (iResult) {
            ResultTypeFirebase.REGISTRATION_DONE -> {
                // cadastro realizado, envia o e-mail de verificacao
                iFirebaseAuth.sendEmailVerification(weakReference.get()!!)
            }
            ResultTypeFirebase.VERIFICATION_EMAIL_SENT -> {
                // e-mail enviado
                verificationEmailSent()
                _registerResult.postValue(ResultTypeFirebase.REGISTRATION_DONE)
            }
            else -> {
                _registerResult.postValue(iResult)
            }
        }
    }
}