package br.com.ernanilima.jinventario;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoApp;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoMain;

public class AppActivity extends AppCompatActivity {
    // exibe todos os fragment da tela do aplicativo
    // tudo configurado no xml

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        //INICIALIZA
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // informar os IDs dos itens de menus associados ao drawer Layout.
        // ao criar a navegacao, os IDs deves ser os mesmos IDs abaixo.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_inicio, R.id.nav_contagem)
                .setOpenableLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.fragment_app);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // botao que nao tem fragment para navegacao
        navigationView.getMenu().findItem(R.id.nav_politica_privacidade).setOnMenuItemClickListener(item -> abrirPoliticaPrivacidade());
        navigationView.getMenu().findItem(R.id.nav_configuracao).setOnMenuItemClickListener(item -> abrirConfiguracao());
        navigationView.getMenu().findItem(R.id.nav_sair).setOnMenuItemClickListener(item -> sair());
    }

    @Override
    /** Usado para abrir drawer layout ao pressionar o botao no toolbar */
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment_app);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    /** Abre a pagina de politica de privacidade */
    private boolean abrirPoliticaPrivacidade() {
        drawerLayout.closeDrawers(); // fecha o drawer layout
        Uri uri = Uri.parse(getString(R.string.site_politica_privacidade));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        return true;
    }

    /** Abre a tela de configuracao */
    private boolean abrirConfiguracao() {
        drawerLayout.closeDrawers(); // fecha o drawer layout
        // antes de abrir a configuracao, a tela inicial eh aberta para que o retorno
        // da configuracao seja a tela inicial caso seja pressionado o botao voltar
        NavegacaoApp.abrirTelaInicioApp(getCurrentFocus());
        NavegacaoApp.abrirTelaConfiguracao(getCurrentFocus());
        return true;
    }

    private boolean sair() {
        FirebaseAuth.getInstance().signOut();
        NavegacaoMain.abrirTelaActivityMain(this);
        return true;
    }
}
