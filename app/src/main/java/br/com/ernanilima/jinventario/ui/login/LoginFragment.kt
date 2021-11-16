package br.com.ernanilima.jinventario.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.databinding.FragmentLoginBinding
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoNomeAparelho
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toForgotPasswordFragment
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toRegisterFragment
import br.com.ernanilima.jinventario.data.network.google.Google
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import br.com.ernanilima.jinventario.extension.common.*
import br.com.ernanilima.jinventario.extension.common.dialog.QuestionDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

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
        Google.showActivityForResult(this)

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
                ResultTypeFirebase.LOGIN_DONE -> {
                    NavegacaoApp.abrirTelaActivityApp(requireActivity())
                }
                ResultTypeFirebase.FIRST_LOGIN_DONE -> {
                    NavegacaoNomeAparelho.abrirTelaActivityNomeAparelho(requireActivity())
                }
                ResultTypeFirebase.EMAIL_NOT_VERIFIED -> {
                    // ADICIONAR LOADING AO ENVIAR EMAIL
                    SimpleDialog(QuestionDialog(requireActivity().supportFragmentManager).apply {
                        setMessage("Reenviar e-mail de verificação?")
                        setPositiveButton {
                            loginViewModel.sendEmailVerification()
                        }
                    }).show()
                }
                ResultTypeFirebase.VERIFICATION_EMAIL_SENT -> {
                    ToastPersonalizado.sucesso(requireContext(), MensagensAlerta.EMAIL_VERIFICACAO_ENVIADO.msg)
                }
                ResultTypeLocal.WAIT_SEND_VERIFICATION -> {
                    ToastPersonalizado.erro(requireContext(), MensagensAlerta.getMsgTempoEsperaEmail(loginViewModel.waitingTime))
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
        loginViewModel.loginGmail()
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
