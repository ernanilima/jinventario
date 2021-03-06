package br.com.ernanilima.jinventario.ui.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.data.network.firebase.validation.FirebaseError
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase.NEW_PASSWORD_EMAIL_SENT
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.WAIT_SEND_PASSWORD
import br.com.ernanilima.jinventario.databinding.FragmentLoginForgotPasswordBinding
import br.com.ernanilima.jinventario.extension.common.InputHelper
import br.com.ernanilima.jinventario.extension.common.Validator
import br.com.ernanilima.jinventario.extension.common.dialog.LoadingDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.extension.common.ifFalse
import br.com.ernanilima.jinventario.extension.common.ifTrue
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom.success
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom.warning
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toLoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentLoginForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private var _loadingDialog: SimpleDialog? = null
    private val loadingDialog get() = _loadingDialog!!
    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUi() {
        // ACAO DE BOTOES
        binding.btnSend.setOnClickListener { sendNewPassword() }
        binding.btnBack.setOnClickListener { toLoginFragment(this) }
        _loadingDialog = SimpleDialog(LoadingDialog(parentFragmentManager))

        // REQUISICOES DO CAMPO E-MAIL
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutEmail)
            setRequired(true)
        }.build()
    }

    private fun setupListener() {
        forgotPasswordViewModel.isInternet.observe(viewLifecycleOwner, { result ->
            result.ifFalse {
                val context = requireParentFragment().requireContext()
                warning(context, binding.root, getString(R.string.msg_without_internet))
                loadingDialog.close()
            }
        })

        forgotPasswordViewModel.forgotPasswordResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                NEW_PASSWORD_EMAIL_SENT -> {
                    val context = requireParentFragment().requireContext()
                    success(context, binding.root, getString(R.string.msg_email_update_password_sent))
                    toLoginFragment(this)
                }
                WAIT_SEND_PASSWORD -> {
                    val context = requireParentFragment().requireContext()
                    warning(context, binding.root, getString(R.string.msg_waiting_time, forgotPasswordViewModel.waitingTime))
                }
                // NEW_PASSWORD_EMAIL_NOT_SENT -> { RESULTADO EH EXIBO NO TRATAMENTO DE ERROS }
            }
            loadingDialog.close()
        })

        forgotPasswordViewModel.forgotPasswordResultFirebaseError.observe(viewLifecycleOwner, { result ->
            val context = requireParentFragment().requireContext()
            warning(context, binding.root, FirebaseError.getMessage(result, context))
        })
    }

    /**
     * Realiza o envio de uma nova senha com base no e-mail
     */
    private fun sendNewPassword() {
        validate().ifTrue {
            loadingDialog.show()
            forgotPasswordViewModel.user.email = binding.fieldEmail.text.toString().trim()
            forgotPasswordViewModel.sendNewPassword()
        }
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

        // se ja tiver outros erros
        binding.layoutEmail.isErrorEnabled.ifTrue { isValid = !it }

        return isValid
    }
}