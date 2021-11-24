package br.com.ernanilima.jinventario.service.navcontroller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import br.com.ernanilima.jinventario.ui.AppActivity
import br.com.ernanilima.jinventario.R
import br.com.ernanilima.jinventario.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Navegacoes
 */
class Navigation {

    /**
     * Navegacoes na activity de login (main)
     */
    class Login {
        companion object {

            /**
             * Navega para a activity [br.com.ernanilima.jinventario.ui.MainActivity]
             * @param currentContext Context - context/view atual
             */
            fun toLoginActivity(currentContext: Context) {
                val currentActivity = currentContext as Activity
                val intent = Intent(currentActivity, MainActivity::class.java)
                currentActivity.startActivity(intent)
                currentActivity.finish()
            }

            /**
             * Navega para o fragment [br.com.ernanilima.jinventario.ui.login.LoginFragment]
             * @param currentFragment Fragment - fragment/view atual
             */
            fun toLoginFragment(currentFragment: Fragment) {
                FirebaseAuth.getInstance().signOut()
                NavHostFragment.findNavController(currentFragment).popBackStack()
                NavHostFragment.findNavController(currentFragment).apply {
                    popBackStack()
                    navigate(R.id.viewLogin)
                }
            }

            /**
             * Navega para o fragment [br.com.ernanilima.jinventario.ui.forgotPassword.ForgotPasswordFragment]
             * @param currentFragment Fragment - fragment/view atual
             */
            fun toForgotPasswordFragment(currentFragment: Fragment) {
                NavHostFragment.findNavController(currentFragment).apply {
                    popBackStack(R.id.viewForgotPassword, true)
                    navigate(R.id.viewForgotPassword)
                }
            }

            /**
             * Navega para o fragment [br.com.ernanilima.jinventario.ui.register.RegisterFragment]
             * @param currentFragment Fragment - fragment/view atual
             */
            fun toRegisterFragment(currentFragment: Fragment) {
                NavHostFragment.findNavController(currentFragment).apply {
                    popBackStack(R.id.viewRegister, true)
                    navigate(R.id.viewRegister)
                }
            }

            /**
             * Navega para o fragment [br.com.ernanilima.jinventario.ui.deviceName.DeviceNameFragment]
             * @param currentFragment Fragment - fragment/view atual
             */
            fun toDeviceNameFragment(currentFragment: Fragment) {
                NavHostFragment.findNavController(currentFragment).apply {
                    popBackStack(R.id.viewDeviceName, true)
                    navigate(R.id.viewDeviceName)
                }
            }
        }
    }

    /**
     * Navegacoes na activity do app (login ja realizado)
     */
    class App {
        companion object {

            /**
             * Navega para a activity [br.com.ernanilima.jinventario.ui.AppActivity]
             * @param currentActivity Activity - activity/view atual
             */
            fun toHomeActivity(currentActivity: Activity) {
                val intent = Intent(currentActivity, AppActivity::class.java)
                currentActivity.startActivity(intent)
                currentActivity.finish()
            }

            /**
             * Navega para o fragment [br.com.ernanilima.jinventario.view.ConfiguracaoFragment]
             * @param currentView View - view da navegacao
             */
            fun toSettingsFragment(currentView: View) {
                Navigation.findNavController(currentView).apply {
                    popBackStack()
                    navigate(R.id.nav_configuracao)
                }
            }
        }
    }
}