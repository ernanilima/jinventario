package br.com.ernanilima.jinventario.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.firebase.FirebaseAutenticacao
import br.com.ernanilima.jinventario.firebase.IFirebaseAutenticacao
import br.com.ernanilima.jinventario.firebase.TipoResultado
import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp
import br.com.ernanilima.jinventario.service.validation.ValidarInternet
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private var iFirebaseAutenticacao: IFirebaseAutenticacao
): ViewModel(), ILogin.IViewModel {

    init {
        this.iFirebaseAutenticacao = FirebaseAutenticacao(this)
    }

    private val weakReference = WeakReference(context)

    var user: User = User()
        private set

    override fun login() {
        if (validarInternet()) {
            iFirebaseAutenticacao.loginUsuario(weakReference.get(), user.email, user.password)
        }
    }

    @Deprecated(message = "Criar nova forma de usar esse metodo")
    private fun validarInternet(): Boolean {
        val internet: Boolean = ValidarInternet.conexao(weakReference.get())
        if (!internet) {
            ToastPersonalizado.erro(weakReference.get(), MensagensAlerta.SEM_INTERNET.msg)
        }
        return internet
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