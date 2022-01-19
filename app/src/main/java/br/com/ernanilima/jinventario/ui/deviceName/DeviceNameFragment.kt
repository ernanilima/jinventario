package br.com.ernanilima.jinventario.ui.deviceName

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.SAVED_DEVICE_NAME
import br.com.ernanilima.jinventario.databinding.FragmentLoginDeviceNameBinding
import br.com.ernanilima.jinventario.extension.common.InputHelper
import br.com.ernanilima.jinventario.extension.common.Validator
import br.com.ernanilima.jinventario.extension.common.dialog.LoadingDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.extension.common.ifTrue
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.App.Companion.toHomeActivity
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toLoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceNameFragment : Fragment() {

    private var _binding: FragmentLoginDeviceNameBinding? = null
    private val binding get() = _binding!!
    private var _loadingDialog: SimpleDialog? = null
    private val loadingDialog get() = _loadingDialog!!
    private val deviceNameViewModel: DeviceNameViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginDeviceNameBinding.inflate(inflater, container, false)
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
        binding.btnSave.setOnClickListener { saveDeviceName() }
        binding.btnBack.setOnClickListener { toLoginFragment(this) }
        _loadingDialog = SimpleDialog(LoadingDialog(parentFragmentManager))

        // MANIPILAR BOTAO VOLTAR DO APARELHO
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            isEnabled.ifTrue { toLoginFragment(this@DeviceNameFragment) }
        }

        // REQUISICOES DO CAMPO NOME DO APARELHO
        InputHelper(requireActivity()).apply {
            setInputLayout(binding.layoutDeviceName)
            setRequired(true)
            setMinLength(5)
        }.build()
    }

    private fun setupListener() {
        deviceNameViewModel.deviceNameResult.observe(viewLifecycleOwner, { result ->
            if (result == SAVED_DEVICE_NAME) {
                toHomeActivity(requireActivity())
                loadingDialog.close()
            }
        })
    }

    /**
     * Grava o nome do aparelho
     */
    private fun saveDeviceName() {
        validate().ifTrue {
            loadingDialog.show()
            deviceNameViewModel.user.deviceName = binding.fieldDeviceName.text.toString().trim()
            deviceNameViewModel.saveDeviceName()
        }
    }

    /**
     * Realiza a validacao de campos obrigatorios
     * Exibe erros caso exista
     * @return Boolean - true se for valido
     */
    private fun validate(): Boolean {
        var isValid = true

        // campo nome do aparelho
        Validator.apply {
            isEmpty(binding.fieldDeviceName.text.toString()).ifTrue {
                showError(binding.layoutDeviceName, requireActivity())
                isValid = !it
            }
        }

        // se ja tiver outros erros
        binding.layoutDeviceName.isErrorEnabled.ifTrue { isValid = !it }

        return isValid
    }
}