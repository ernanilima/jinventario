package br.com.ernanilima.jinventario.ui.register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.data.network.firebase.validation.FirebaseError
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase.REGISTRATION_DONE
import br.com.ernanilima.jinventario.databinding.FragmentLoginRegisterBinding
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
class RegisterFragment : Fragment() {

    private var _binding: FragmentLoginRegisterBinding? = null
    private val binding get() = _binding!!
    private var _loadingDialog: SimpleDialog? = null
    private val loadingDialog get() = _loadingDialog!!
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginRegisterBinding.inflate(inflater, container, false)
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
        binding.btnPrivacyPolicy.setOnClickListener { showPrivacyPolicy() }
        binding.btnRegister.setOnClickListener { register() }
        binding.btnBack.setOnClickListener { toLoginFragment(this) }
        _loadingDialog = SimpleDialog(LoadingDialog(parentFragmentManager))

        // REQUISICOES DO CAMPO E-MAIL
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutEmail)
            setRequired(true)
        }.build()

        // REQUISICOES DO CAMPO SENHA 1
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutPassword1)
            setInputLayoutForMatch(binding.layoutPassword2)
            setRequired(true)
            setMinLength(6)
        }.build()

        // REQUISICOES DO CAMPO SENHA 2
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutPassword2)
            setInputLayoutForMatch(binding.layoutPassword1)
            setRequired(true)
            setMinLength(6)
        }.build()
    }

    private fun setupListener() {
        registerViewModel.isInternet.observe(viewLifecycleOwner, { result ->
            result.ifFalse {
                val context = requireParentFragment().requireContext()
                warning(context, binding.root, getString(R.string.msg_without_internet))
                loadingDialog.close()
            }
        })

        registerViewModel.registerResult.observe(viewLifecycleOwner, { result ->
            if (result == REGISTRATION_DONE) {
                val context = requireParentFragment().requireContext()
                success(context, binding.root, getString(R.string.msg_registered_user))
                toLoginFragment(this)
            }
            loadingDialog.close()
        })

        registerViewModel.registerResultFirebaseError.observe(viewLifecycleOwner, { result ->
            val context = requireParentFragment().requireContext()
            warning(context, binding.root, FirebaseError.getMessage(result, context))
        })
    }

    /**
     * Exibe a politica de privacidade
     */
    private fun showPrivacyPolicy() {
        val uri = Uri.parse(getString(R.string.link_privacy_policy))
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    /**
     * Realiza o cadastro com base no e-mail e senha
     */
    private fun register() {
        validate().ifTrue {
            loadingDialog.show()
            registerViewModel.user.email = binding.fieldEmail.text.toString().trim()
            registerViewModel.user.password = binding.fieldPassword1.text.toString()
            registerViewModel.register()
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

        // campo senha 1
        Validator.apply {
            isEmpty(binding.fieldPassword1.text.toString()).ifTrue {
                showError(binding.layoutPassword1, requireActivity())
                isValid = !it
            }
        }

        // campo senha 2
        Validator.apply {
            isEmpty(binding.fieldPassword2.text.toString()).ifTrue {
                showError(binding.layoutPassword2, requireActivity())
                isValid = !it
            }
        }

        // se ja tiver outros erros
        binding.layoutEmail.isErrorEnabled.ifTrue { isValid = !it }
        binding.layoutPassword1.isErrorEnabled.ifTrue { isValid = !it }
        binding.layoutPassword2.isErrorEnabled.ifTrue { isValid = !it }

        isValid.ifTrue { // se a validacao abaixo for a ultima a ser realizada
            // politica de privacidade
            binding.chbxPrivacyPolicy.isChecked.ifFalse {
                val context = requireParentFragment().requireContext()
                warning(context, binding.root, getString(R.string.msg_privacy_policy))
                isValid = it
            }
        }

        return isValid
    }
}