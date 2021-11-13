package br.com.ernanilima.jinventario.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.databinding.FragmentLoginBinding
import br.com.ernanilima.jinventario.extension.common.InputHelper
import br.com.ernanilima.jinventario.extension.common.Validator
import br.com.ernanilima.jinventario.extension.common.ifFalse
import br.com.ernanilima.jinventario.extension.common.ifTrue
import br.com.ernanilima.jinventario.data.network.firebase.TipoResultado
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoNomeAparelho
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toForgotPasswordFragment
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toRegisterFragment
import br.com.ernanilima.jinventario.data.network.google.Google
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUi() {
        // ACAO DE BOTOES
        binding.fieldPassword.setOnEditorActionListener { _, type, _ -> (type == IME_ACTION_DONE).ifTrue { login() } }
        binding.btnLogin.setOnClickListener { binding.progressLogin.isVisible.ifFalse { login() } }
        binding.btnLoginGmail.setOnClickListener { binding.progressLogin.isVisible.ifFalse { loginGmail() } }
        binding.btnForgotPassword.setOnClickListener { binding.progressLogin.isVisible.ifFalse { toForgotPasswordFragment(this) } }
        binding.btnRegister.setOnClickListener { binding.progressLogin.isVisible.ifFalse { toRegisterFragment(this) } }

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

    private fun setupListener() {
        loginViewModel.isInternet.observe(viewLifecycleOwner, { result ->
            result.ifFalse {
                ToastPersonalizado.erro(activity, MensagensAlerta.SEM_INTERNET.msg)
                binding.progressLogin.visibility = View.GONE
            }
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                TipoResultado.LOGIN_REALIZADO -> {
                    NavegacaoApp.abrirTelaActivityApp(requireActivity())
                }
                TipoResultado.FIRST_LOGIN -> {
                    NavegacaoNomeAparelho.abrirTelaActivityNomeAparelho(requireActivity())
                }
                TipoResultado.EMAIL_NAO_VERIFICADO -> {
                    // PENDENTE: CRIAR CLASSE PARA ENVIOS
                    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("E-mail não verificado!")
                        .setMessage("Reenviar e-mail de verificação?")
                        .setPositiveButton("Sim") { _, _ -> loginViewModel.sendEmailVerification() }
                        .setNegativeButton("Não") { dialog, _ -> dialog.cancel() }
                        .setCancelable(false)

                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.show()
                }
                TipoResultado.EMAIL_VERIFICACAO_ENVIADO -> {
                    ToastPersonalizado.sucesso(requireContext(), MensagensAlerta.EMAIL_VERIFICACAO_ENVIADO.msg)
                }
                else -> {}
            }
            binding.progressLogin.visibility = View.GONE
        })
    }

    /**
     * Realiza o login com base no email e senha
     */
    private fun login() {
        validate().ifTrue {
            binding.progressLogin.visibility = View.VISIBLE
            val userEmail = binding.fieldEmail.text.toString().trim()
            val userPassword = binding.fieldPassword.text.toString()
            loginViewModel.login(userEmail, userPassword)
        }
    }

    /**
     * Realiza o login com base no usuario do Gmail
     */
    private fun loginGmail() {
        binding.progressLogin.visibility = View.VISIBLE
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
