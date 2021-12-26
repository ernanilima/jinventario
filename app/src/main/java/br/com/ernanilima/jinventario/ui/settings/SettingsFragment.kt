package br.com.ernanilima.jinventario.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

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
        setupUi()
        setupListener()
    }

    private fun setupUi() {
        // ACAO DE BOTOES
        binding.chbxCameraScanner.setOnCheckedChangeListener { _, isChecked -> useCameraAsScanner(isChecked) }
        binding.btnSave.setOnClickListener { saveSettings() }
    }

    private fun setupListener() {

    }

    /**
     * Usado para ativar/desativar radio button de tipo de camera
     * @param isChecked boolean - checkbox de usar camera ativado/desativado
     */
    private fun useCameraAsScanner(isChecked: Boolean) {
        binding.radioCameraMlkit.isEnabled = isChecked
        binding.radioCameraZxing.isEnabled = isChecked
    }

    private fun saveSettings() {

    }
}