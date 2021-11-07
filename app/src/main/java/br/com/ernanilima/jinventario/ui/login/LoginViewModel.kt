package br.com.ernanilima.jinventario.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.firebase.FirebaseAutenticacao
import br.com.ernanilima.jinventario.firebase.IFirebaseAutenticacao
import br.com.ernanilima.jinventario.firebase.TipoResultado
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp
import br.com.ernanilima.jinventario.service.social.Google
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado
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

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAutenticacao = FirebaseAutenticacao(this)
    }

    override fun login(userEmail: String, userPassword: String) {
        if (DeviceHelper.isInternet(weakReference.get())) {
            iFirebaseAutenticacao.loginUsuario(weakReference.get(), userEmail, userPassword)
        }
    }

    override fun loginGoogle(loginGoogle: GoogleSignInClient) {
        if (DeviceHelper.isInternet(weakReference.get())) {
            Google.getInstance().loginGoogle(loginGoogle, this)
        }
    }

    override fun setResultado(resultado: TipoResultado) {
        when (resultado) {
            TipoResultado.LOGIN_REALIZADO -> {
                // se nome do aparelho ja existir, abre a tela inicial do app com as contagens
//                if (NomeAparelhoAutenticacao.getInstance(daoSession).getNomeExiste()) {
                    NavegacaoApp.abrirTelaActivityApp(weakReference.get())
//                } else {
//                    NavegacaoNomeAparelho.abrirTelaActivityNomeAparelho(weakReference.get())
//                }
            }
//            TipoResultado.EMAIL_NAO_VERIFICADO -> dialogEmailVerificacao()
            TipoResultado.EMAIL_VERIFICACAO_ENVIADO -> ToastPersonalizado.sucesso(
                weakReference.get(),
                MensagensAlerta.EMAIL_VERIFICACAO_ENVIADO.msg
            )
        }
    }
}