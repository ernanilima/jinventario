package br.com.ernanilima.jinventario.service.navcontroller

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import br.com.ernanilima.jinventario.AppActivity
import br.com.ernanilima.jinventario.NomeAparelhoActivity
import br.com.ernanilima.jinventario.R

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
             * Navega para o fragment [br.com.ernanilima.jinventario.ui.login.LoginFragment]
             * @param currentFragment Fragment - fragment/view atual
             */
            fun toLoginFragment(currentFragment: Fragment) {
                NavHostFragment.findNavController(currentFragment).popBackStack()
                NavHostFragment.findNavController(currentFragment).apply {
                    popBackStack()
                    navigate(R.id.telaLogin)
                }
            }

            /**
             * Navega para o fragment [br.com.ernanilima.jinventario.view.EsqueceuSenhaFragment]
             * @param currentFragment Fragment - fragment/view atual
             */
            fun toForgotPasswordFragment(currentFragment: Fragment) {
                NavHostFragment.findNavController(currentFragment).apply {
                    popBackStack(R.id.telaEsqueceuSenha, true)
                    navigate(R.id.telaEsqueceuSenha)
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
             * SERA ALTERADO PARA SER UM FRAGMENT DE MAIN/LOGIN
             */
            fun toDeviceNameActivity(currentactivity: Activity) {
                val intent = Intent(currentactivity, NomeAparelhoActivity::class.java)
                currentactivity.startActivity(intent)
                currentactivity.finish()
            }
        }
    }

    /**
     * Navegacoes na activity do app (login ja realizado)
     */
    class App {
        companion object {

            /**
             * Navega para a activity [br.com.ernanilima.jinventario.AppActivity]
             * @param currentactivity Activity - activity/view atual
             */
            fun toHomeActivity(currentactivity: Activity) {
                val intent = Intent(currentactivity, AppActivity::class.java)
                currentactivity.startActivity(intent)
                currentactivity.finish()
            }
        }
    }
}