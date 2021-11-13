package br.com.ernanilima.jinventario.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.TipoResultado
import br.com.ernanilima.jinventario.repository.UserRepository
import br.com.ernanilima.jinventario.data.network.google.Google
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
    private var iFirebaseAuth: IFirebaseAuth
): ViewModel(), ILogin.IViewModel {

    private val weakReference = WeakReference(context)

    private val _isInternet = MutableLiveData<Boolean>()
    val isInternet: LiveData<Boolean> = _isInternet

    private val _loginResult = MutableLiveData<TipoResultado>()
    val loginResult: LiveData<TipoResultado> = _loginResult

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
            _loginResult.postValue(TipoResultado.UNAUTHENTICATED_USER)
        }
    }

    /* Verifica se tem internet e realiza login com o gmail */
    override fun loginGmail(loginGmail: GoogleSignInClient) {
        if (DeviceHelper.isInternet(weakReference.get())) {
            Google.getInstance().loginGoogle(loginGmail, this)
        } else {
            _isInternet.postValue(false)
            _loginResult.postValue(TipoResultado.UNAUTHENTICATED_USER)
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
            // se o e-mail nao puder ser enviado, exibe um toast com o tempo que o usuario deve aguardar para um novo envio
            println("PENDENTE")
        }
    }

    override fun setResultado(result: TipoResultado) {
        when (result) {
            TipoResultado.LOGIN_REALIZADO -> {
                // TODO - PENDENTE: LOGIN COM O GMAIL GERA UM E-MAIL NULL
                if (userDao.findByEmail(userEmail!!).deviceName.isNullOrBlank()) {
                    _loginResult.postValue(TipoResultado.FIRST_LOGIN)
                } else {
                    _loginResult.postValue(result)
                }
            }
            else -> {
                _loginResult.postValue(result)
            }
        }
    }
}