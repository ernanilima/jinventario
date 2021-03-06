package br.com.ernanilima.jinventario.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.data.result.ResultTypeSettings.REFRESH_SETTINGS
import br.com.ernanilima.jinventario.data.result.ResultTypeSettings.SAVED_SETTINGS
import br.com.ernanilima.jinventario.databinding.FragmentSettingsBinding
import br.com.ernanilima.jinventario.extension.common.dialog.LoadingDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.service.navcontroller.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var _loadingDialog: SimpleDialog? = null
    private val loadingDialog get() = _loadingDialog!!
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delay()
        setupUi()
        setupListener()
    }

    private fun delay() {
        CoroutineScope(Dispatchers.Main).launch {
            loadingDialog.show()
            delay(1000)
            loadingDialog.close()
        }
    }

    private fun setupUi() {
        // ACAO DE BOTOES
        binding.chbxCameraScanner.setOnCheckedChangeListener { _, isChecked -> useCameraAsScanner(isChecked) }
        binding.btnSave.setOnClickListener { saveSettings() }
        _loadingDialog = SimpleDialog(LoadingDialog(parentFragmentManager))
    }

    private fun setupListener() {
        settingsViewModel.settingsResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                REFRESH_SETTINGS -> {
                    binding.chbxShowPrice.isChecked = settingsViewModel.settings.showPrice
                    binding.chbxCameraScanner.isChecked = settingsViewModel.settings.cameraScanner
                    binding.radioCameraMlkit.isChecked = settingsViewModel.settings.cameraScannerMlkit
                    binding.radioCameraZxing.isChecked = settingsViewModel.settings.cameraScannerZxing
                }
                SAVED_SETTINGS -> {
                    loadingDialog.close()
                    Navigation.App.toHomeActivity(requireActivity())
                }
            }
        })
    }

    /**
     * Usado para ativar/desativar radio button de tipo de camera
     * @param isChecked boolean - checkbox de usar camera ativado/desativado
     */
    private fun useCameraAsScanner(isChecked: Boolean) {
        binding.radioCameraMlkit.isEnabled = isChecked
        binding.radioCameraZxing.isEnabled = isChecked
    }

    /**
     * Grava as configuracoes
     */
    private fun saveSettings() {
        loadingDialog.show()
        settingsViewModel.settings.showPrice = binding.chbxShowPrice.isChecked
        settingsViewModel.settings.cameraScanner = binding.chbxCameraScanner.isChecked
        settingsViewModel.settings.cameraScannerMlkit = binding.radioCameraMlkit.isChecked
        settingsViewModel.settings.cameraScannerZxing = binding.radioCameraZxing.isChecked
        settingsViewModel.saveSettings()
    }
}