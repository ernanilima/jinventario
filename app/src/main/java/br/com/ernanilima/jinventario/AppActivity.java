package br.com.ernanilima.jinventario;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class AppActivity extends AppCompatActivity {

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
        drawerLayout = findViewById(R.id.drawer_Layout);
        navigationView = findViewById(R.id.nav_view);

        // informar os IDs dos itens de menus associados ao drawer Layout.
        // ao criar a navegacao, os IDs deves ser os mesmos IDs abaixo.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.btn_menu_nav_inicio, R.id.btn_menu_nav_novacoletagem)
                .setOpenableLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.fragment_app);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // botao nao tem fragment para navegacao
        navigationView.getMenu().findItem(R.id.btn_menu_nav_sair).setOnMenuItemClickListener(item -> sair());
    }

    @Override
    /** Usado para abrir drawer Layout ao pressionar o botao no toolbar */
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment_app);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private boolean sair() {
        System.out.println("SAIR DO APLICATIVO");
        return true;
    }
}
