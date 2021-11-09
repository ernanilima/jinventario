package br.com.ernanilima.jinventario.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.databinding.FragmentSplashBinding
import br.com.ernanilima.jinventario.extension.common.ifFalse
import br.com.ernanilima.jinventario.firebase.TipoResultado.AUTHENTICATED_USER
import br.com.ernanilima.jinventario.firebase.TipoResultado.UNAUTHENTICATED_USER
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoMain
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        splashViewModel.checkAuthenticatedUserToLogin()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupListener() {
        splashViewModel.isInternet.observe(viewLifecycleOwner, { result ->
            result.ifFalse {
                ToastPersonalizado.erro(activity, MensagensAlerta.SEM_INTERNET.msg)
            }
        })

        splashViewModel.automaticLoginResult.observe(viewLifecycleOwner, { result ->
            when(result) {
                AUTHENTICATED_USER -> {
                    NavegacaoApp.abrirTelaActivityApp(requireContext())
                }
                UNAUTHENTICATED_USER -> {
                    NavegacaoMain.abrirTelaLogin(requireView())
                }
            }
        })
    }
}