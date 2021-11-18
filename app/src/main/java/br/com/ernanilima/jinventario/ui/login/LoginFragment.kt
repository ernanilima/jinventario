package br.com.ernanilima.jinventario.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.databinding.FragmentLoginBinding
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toForgotPasswordFragment
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toRegisterFragment
import br.com.ernanilima.jinventario.data.network.google.Google
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import br.com.ernanilima.jinventario.extension.common.*
import br.com.ernanilima.jinventario.extension.common.dialog.QuestionDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom
import br.com.ernanilima.jinventario.service.navcontroller.Navigation
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
        binding.btnLogin.setOnClickListener { binding.progressLogin.isVisible.ifFalse { login() } }
        binding.btnLoginGmail.setOnClickListener { binding.progressLogin.isVisible.ifFalse { loginGmail() } }
        binding.btnForgotPassword.setOnClickListener { binding.progressLogin.isVisible.ifFalse { toForgotPasswordFragment(this) } }
        binding.btnRegister.setOnClickListener { binding.progressLogin.isVisible.ifFalse { toRegisterFragment(this) } }

        // GOOGLE
        Google.showActivityForResult(this)

        // REQUISICOES DO CAMPO E-MAIL
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutEmail)
            setRequired(true)
        }.build()

        // REQUISICOES DO CAMPO SENHA
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutPassword)
            setRequired(true)
            setMinLength(6)
        }.build()
    }

    private fun setupListener() {
        loginViewModel.isInternet.observe(viewLifecycleOwner, { result ->
            result.ifFalse {
                val context = requireParentFragment().requireContext()
                SnackbarCustom.warning(context, getString(R.string.msg_without_internet))
                binding.progressLogin.visibility = View.GONE
            }
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                ResultTypeFirebase.LOGIN_DONE -> {
                    Navigation.App.toHomeActivity(requireActivity())
                }
                ResultTypeFirebase.FIRST_LOGIN_DONE -> {
                    Navigation.Login.toDeviceNameActivity(requireActivity())
                }
                ResultTypeFirebase.EMAIL_NOT_VERIFIED -> {
                    SimpleDialog(QuestionDialog(parentFragmentManager).apply {
                        setMessage(getString(R.string.s_dialog_msg_email_verification))
                        setPositiveButton {
                            binding.progressLogin.visibility = View.VISIBLE
                            loginViewModel.sendEmailVerification()
                        }
                    }).show()
                }
                ResultTypeFirebase.VERIFICATION_EMAIL_SENT -> {
                    val context = requireParentFragment().requireContext()
                    SnackbarCustom.success(context, getString(R.string.msg_email_verification_sent))
                }
                ResultTypeLocal.WAIT_SEND_VERIFICATION -> {
                    val context = requireParentFragment().requireContext()
                    SnackbarCustom.warning(context, getString(R.string.msg_waiting_time, loginViewModel.waitingTime))
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
            loginViewModel.user.email = binding.fieldEmail.text.toString().trim()
            loginViewModel.user.password = binding.fieldPassword.text.toString()
            loginViewModel.login()
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

        // campo e-mail
        Validator.apply {
            isEmpty(binding.fieldEmail.text.toString()).ifTrue {
                showError(binding.layoutEmail, requireActivity())
                isValid = !it
            }
        }

        // campo senha
        Validator.apply {
            isEmpty(binding.fieldPassword.text.toString()).ifTrue {
                showError(binding.layoutPassword, requireActivity())
                isValid = !it
            }
        }

        // se ja tiver outros erros
        binding.layoutEmail.isErrorEnabled.ifTrue { isValid = !it }
        binding.layoutPassword.isErrorEnabled.ifTrue { isValid = !it }

        return isValid
    }
}
