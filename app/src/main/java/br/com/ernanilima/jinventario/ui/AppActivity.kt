package br.com.ernanilima.jinventario.ui

import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.ernanilima.jinventario.R
import android.content.Intent
import android.net.Uri
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import br.com.ernanilima.jinventario.databinding.ActivityAppBinding
import br.com.ernanilima.jinventario.extension.common.DeviceHelper
import br.com.ernanilima.jinventario.extension.common.dialog.QuestionDialog
import br.com.ernanilima.jinventario.extension.common.dialog.SimpleDialog
import br.com.ernanilima.jinventario.model.Settings
import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.App.Companion.toSettingsFragment
import br.com.ernanilima.jinventario.service.navcontroller.Navigation.Login.Companion.toLoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    /**
     * Definicoes do usuario logado
     */
    companion object {
        var user: User? = null
        var settings: Settings? = null
    }

    private lateinit var binding: ActivityAppBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBar.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_app)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_stock_count
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // botao que nao tem fragment para navegacao
        navView.menu.findItem(R.id.nav_politica_privacidade).setOnMenuItemClickListener { privacyPolicy() }
        navView.menu.findItem(R.id.nav_configuracao).setOnMenuItemClickListener { settings() }
        navView.menu.findItem(R.id.nav_sair).setOnMenuItemClickListener { singOut() }
    }

    /**
     * Abre drawer layout ao pressionar o botao na toolbar
     */
    override fun onSupportNavigateUp(): Boolean {
        DeviceHelper.hideKeyboard(this)
        val navController = findNavController(R.id.nav_host_fragment_content_app)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Abre a politica de privacidade
     */
    private fun privacyPolicy(): Boolean {
        binding.drawerLayout.closeDrawers()
        val uri = Uri.parse(getString(R.string.link_privacy_policy))
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
        return true
    }

    /**
     * Abre a tela de configuracoes
     */
    private fun settings(): Boolean {
        binding.drawerLayout.closeDrawers()
        toSettingsFragment(findViewById(R.id.nav_host_fragment_content_app))
        return true
    }

    /**
     * Exibe um dialog para o usuario confirmar que deseja sair
     */
    private fun singOut(): Boolean {
        binding.drawerLayout.closeDrawers()
        SimpleDialog(QuestionDialog(supportFragmentManager).apply {
            setMessage(getString(R.string.s_dialog_msg_sing_out))
            setPositiveButton {
                FirebaseAuth.getInstance().signOut()
                toLoginActivity(this@AppActivity)
            }
        }).show()
        return true
    }
}