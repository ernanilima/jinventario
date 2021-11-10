package br.com.ernanilima.jinventario.service.navcontroller

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
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
             * Navega para o fragmento de [br.com.ernanilima.jinventario.ui.login.LoginFragment]
             * @param currentFragment Fragment - fragmento/view atual
             */
            fun toLoginFragment(currentFragment: Fragment) {
                NavHostFragment.findNavController(currentFragment).popBackStack()
                NavHostFragment.findNavController(currentFragment).apply {
                    popBackStack()
                    navigate(R.id.telaLogin)
                }
            }

            /**
             * Navega para o fragmento de [br.com.ernanilima.jinventario.view.EsqueceuSenhaFragment]
             * @param currentFragment Fragment - fragmento/view atual
             */
            fun toForgotPasswordFragment(currentFragment: Fragment) {
                NavHostFragment.findNavController(currentFragment).apply {
                    popBackStack(R.id.telaEsqueceuSenha, true)
                    navigate(R.id.telaEsqueceuSenha)
                }
            }

            /**
             * Navega para o fragmento de [br.com.ernanilima.jinventario.view.CadastroFragment]
             * @param currentFragment Fragment - fragmento/view atual
             */
            fun toRegisterFragment(currentFragment: Fragment) {
                NavHostFragment.findNavController(currentFragment).apply {
                    popBackStack(R.id.telaCadastro, true)
                    navigate(R.id.telaCadastro)
                }
            }
        }
    }
}