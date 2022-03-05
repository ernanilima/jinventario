package br.com.ernanilima.jinventario.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.databinding.FragmentLoginBinding
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toForgotPasswordFragment
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toRegisterFragment
import br.com.ernanilima.jinventario.data.network.google.Google
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase.*
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.WAIT_SEND_VERIFICATION
import br.com.ernanilima.jinventario.extension.common.*
import br.com.ernanilima.jinventario.extension.common.dialog.LoadingDialog
import br.com.ernanilima.jinventario.extension.common.dialog.QuestionDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom.success
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom.warning
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.App.Companion.toHomeActivity
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toDeviceNameFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var _loadingDialog: SimpleDialog? = null
    private val loadingDialog get() = _loadingDialog!!
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
        binding.btnLogin.setOnClickListener { login() }
        binding.btnLoginGmail.setOnClickListener { loginGmail() }
        binding.btnForgotPassword.setOnClickListener { toForgotPasswordFragment(this) }
        binding.btnRegister.setOnClickListener { toRegisterFragment(this) }
        _loadingDialog = SimpleDialog(LoadingDialog(parentFragmentManager))

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
                warning(context, binding.root, getString(R.string.msg_without_internet))
                loadingDialog.close()
            }
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                LOGIN_DONE -> {
                    toHomeActivity(requireActivity())
                }
                FIRST_LOGIN_DONE -> {
                    toDeviceNameFragment(requireParentFragment())
                }
                EMAIL_NOT_VERIFIED -> {
                    SimpleDialog(QuestionDialog(parentFragmentManager).apply {
                        setMessage(getString(R.string.s_dialog_msg_email_verification))
                        setPositiveButton {
                            loadingDialog.show()
                            loginViewModel.sendEmailVerification()
                        }
                    }).show()
                }
                VERIFICATION_EMAIL_SENT -> {
                    val context = requireParentFragment().requireContext()
                    success(context, binding.root, getString(R.string.msg_email_verification_sent))
                }
                WAIT_SEND_VERIFICATION -> {
                    val context = requireParentFragment().requireContext()
                    warning(context, binding.root, getString(R.string.msg_waiting_time, loginViewModel.waitingTime))
                }
            }
            loadingDialog.dismiss()
        })

        loginViewModel.loginResultFirebaseError.observe(viewLifecycleOwner, { result ->
            println(result)
        })
    }

    /**
     * Realiza o login com base no e-mail e senha
     */
    private fun login() {
        validate().ifTrue {
            loadingDialog.show()
            loginViewModel.user.email = binding.fieldEmail.text.toString().trim()
            loginViewModel.user.password = binding.fieldPassword.text.toString()
            loginViewModel.login()
        }
    }

    /**
     * Realiza o login com base no usuario do Gmail
     */
    private fun loginGmail() {
        loadingDialog.show()
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
