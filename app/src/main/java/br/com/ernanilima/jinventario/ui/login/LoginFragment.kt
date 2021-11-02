package br.com.ernanilima.jinventario.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.databinding.FragmentLoginBinding
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoMain
import br.com.ernanilima.jinventario.service.social.Google
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ACAO DE BOTOES
        binding.campoSenha.setOnClickListener { load() } // botao de teclado
        binding.btnLogin.setOnClickListener { load() }
        binding.btnLoginGmail.setOnClickListener { loginGmail() }
        binding.btnEsqueceuSenha.setOnClickListener { NavegacaoMain.abrirTelaEsqueceuSenha(it) }
        binding.btnCadastrar.setOnClickListener { NavegacaoMain.abrirTelaCadastrar(it) }

        // GOOGLE
        mGoogleSignInClient = Google.getInstance().servicoLoginGoogle(getString(R.string.default_web_client_id), activity)
        Google.getInstance().setFragmentLogin(this)

    }

    private fun loginGmail() {
        super.onStart()
        Google.getInstance().loginGoogle(mGoogleSignInClient, loginViewModel)
    }

    private fun load() {
        loginViewModel.user.email = binding.campoEmail.editText?.text.toString().trim()
        loginViewModel.user.password = binding.campoSenha.editText?.text.toString()
        loginViewModel.login()
    }
}
