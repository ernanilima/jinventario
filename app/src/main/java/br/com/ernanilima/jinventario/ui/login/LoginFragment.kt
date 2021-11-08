package br.com.ernanilima.jinventario.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.databinding.FragmentLoginBinding
import br.com.ernanilima.jinventario.extension.common.InputHelper
import br.com.ernanilima.jinventario.extension.common.Validator
import br.com.ernanilima.jinventario.extension.common.ifTrue
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
        setupUi()
    }

    private fun setupUi() {
        // ACAO DE BOTOES
        binding.fieldPassword.setOnEditorActionListener { _, type, _ -> (type == IME_ACTION_DONE).ifTrue { login() } }
        binding.btnLogin.setOnClickListener { login() }
        binding.btnLoginGmail.setOnClickListener { loginGmail() }
        binding.btnForgotPassword.setOnClickListener { NavegacaoMain.abrirTelaEsqueceuSenha(it) }
        binding.btnRegister.setOnClickListener { NavegacaoMain.abrirTelaCadastrar(it) }

        // GOOGLE
        mGoogleSignInClient = Google.getInstance().servicoLoginGoogle(getString(R.string.default_web_client_id), activity)
        Google.getInstance().setFragmentLogin(this)

        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutEmail)
            setRequired(true)
        }.build()

        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutPassword)
            setRequired(true)
            setMinLength(6)
        }.build()
    }

    /**
     * Realiza o login com base no email e senha
     */
    private fun login() {
        validate().ifTrue {
            val userEmail = binding.fieldEmail.text.toString().trim()
            val userPassword = binding.fieldPassword.text.toString()
            loginViewModel.login(userEmail, userPassword)
        }
    }

    /**
     * Realiza o login com base no usuario do Google
     */
    private fun loginGmail() {
        loginViewModel.loginGmail(mGoogleSignInClient)
    }

    /**
     * Realiza a validacao de campos obrigatorios
     * Exibe erros caso exista
     * @return Boolean - true se for valido
     */
    private fun validate(): Boolean {
        var isValid = true

        Validator.apply {
            isEmpty(binding.fieldEmail.text.toString()).ifTrue {
                showError(binding.layoutEmail, requireActivity())
                isValid = !it
            }
        }

        Validator.apply {
            isEmpty(binding.fieldPassword.text.toString()).ifTrue {
                showError(binding.layoutPassword, requireActivity())
                isValid = !it
            }
        }

        binding.layoutEmail.isErrorEnabled.ifTrue { isValid = !it }
        binding.layoutPassword.isErrorEnabled.ifTrue { isValid = !it }

        return isValid
    }
}
