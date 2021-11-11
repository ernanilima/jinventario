package br.com.ernanilima.jinventario.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.firebase.FirebaseAutenticacao
import br.com.ernanilima.jinventario.firebase.IFirebaseAutenticacao
import br.com.ernanilima.jinventario.firebase.TipoResultado
import br.com.ernanilima.jinventario.service.social.Google
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private var iFirebaseAutenticacao: IFirebaseAutenticacao
): ViewModel(), ILogin.IViewModel {

    private val weakReference = WeakReference(context)

    private val _isInternet = MutableLiveData<Boolean>()
    val isInternet: LiveData<Boolean> = _isInternet

    private val _loginResult = MutableLiveData<TipoResultado>()
    val loginResult: LiveData<TipoResultado> = _loginResult

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAutenticacao = FirebaseAutenticacao(this)
    }

    /* Verifica se tem internet e realiza login */
    override fun login(userEmail: String, userPassword: String) {
        if (DeviceHelper.isInternet(weakReference.get())) {
            iFirebaseAutenticacao.loginUsuario(weakReference.get(), userEmail, userPassword)
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

    override fun setResultado(result: TipoResultado) {
//        when (result) {
//            TipoResultado.LOGIN_REALIZADO -> {
//                if (!NomeAparelhoAutenticacao.getInstance(daoSession).getNomeExiste()) {
//                    _loginResult.postValue(TipoResultado.FIRST_LOGIN)
//                }
//            }
//            else -> {
                _loginResult.postValue(result)
//            }
//        }
    }
}