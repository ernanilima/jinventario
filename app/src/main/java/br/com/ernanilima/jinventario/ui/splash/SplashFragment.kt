package br.com.ernanilima.jinventario.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.data.network.firebase.validation.FirebaseError
import br.com.ernanilima.jinventario.data.result.ResultTypeFirebase.*
import br.com.ernanilima.jinventario.databinding.FragmentSplashBinding
import br.com.ernanilima.jinventario.extension.common.ifFalse
import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom.warning
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.App.Companion.toHomeActivity
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toDeviceNameFragment
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toLoginFragment
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListener() {
        splashViewModel.isInternet.observe(viewLifecycleOwner, { result ->
            result.ifFalse {
                val context = requireParentFragment().requireContext()
                warning(context, binding.root, getString(R.string.msg_without_internet))
            }
        })

        splashViewModel.automaticLoginResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                AUTHENTICATED_USER -> {
                    toHomeActivity(requireActivity())
                }
                FIRST_LOGIN_DONE -> {
                    toDeviceNameFragment(this)
                }
                UNAUTHENTICATED_USER -> {
                    toLoginFragment(this)
                }
            }
        })

        splashViewModel.automaticLoginResultFirebaseError.observe(viewLifecycleOwner, { result ->
            val context = requireParentFragment().requireContext()
            warning(context, binding.root, FirebaseError.getMessage(result, context))
        })
    }
}