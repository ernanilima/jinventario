package br.com.ernanilima.jinventario.ui.deviceName

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.SAVED_DEVICE_NAME
import br.com.ernanilima.jinventario.databinding.FragmentLoginDeviceNameBinding
import br.com.ernanilima.jinventario.extension.common.InputHelper
import br.com.ernanilima.jinventario.extension.common.Validator
import br.com.ernanilima.jinventario.extension.common.ifFalse
import br.com.ernanilima.jinventario.extension.common.ifTrue
import br.com.ernanilima.jinventario.service.navcontroller.Navigation
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toLoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceNameFragment : Fragment() {

    private var _binding: FragmentLoginDeviceNameBinding? = null
    private val binding get() = _binding!!
    private val deviceNameViewModel: DeviceNameViewModel by viewModels()
    private val progressBar get() = binding.progressDeviceName.isVisible

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
        binding.btnSave.setOnClickListener { progressBar.ifFalse { saveDeviceName() } }
        binding.btnBack.setOnClickListener { progressBar.ifFalse { toLoginFragment(this) } }

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
                Navigation.App.toHomeActivity(requireActivity())
                binding.progressDeviceName.visibility = View.GONE
            }
        })
    }

    /**
     * Grava o nome do aparelho
     */
    private fun saveDeviceName() {
        validate().ifTrue {
            binding.progressDeviceName.visibility = View.VISIBLE
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