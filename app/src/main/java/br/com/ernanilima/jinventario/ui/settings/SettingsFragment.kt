package br.com.ernanilima.jinventario.ui.settings

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.REFRESH_SETTINGS
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.SAVED_SETTINGS
import br.com.ernanilima.jinventario.databinding.FragmentSettingsBinding
import br.com.ernanilima.jinventario.service.navcontroller.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
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
            binding.progressSettings.visibility = View.VISIBLE
            delay(1000)
            binding.progressSettings.visibility = View.GONE
        }
    }

    private fun setupUi() {
        // ACAO DE BOTOES
        binding.chbxCameraScanner.setOnCheckedChangeListener { _, isChecked -> useCameraAsScanner(isChecked) }
        binding.btnSave.setOnClickListener { saveSettings() }
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
                    binding.progressSettings.visibility = View.GONE
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
        binding.progressSettings.visibility = View.VISIBLE
        settingsViewModel.settings.showPrice = binding.chbxShowPrice.isChecked
        settingsViewModel.settings.cameraScanner = binding.chbxCameraScanner.isChecked
        settingsViewModel.settings.cameraScannerMlkit = binding.radioCameraMlkit.isChecked
        settingsViewModel.settings.cameraScannerZxing = binding.radioCameraZxing.isChecked
        settingsViewModel.saveSettings()
    }
}