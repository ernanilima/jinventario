package br.com.ernanilima.jinventario;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

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

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.btn_menu_nav_inicio, R.id.btn_menu_nav_novacoletagem)
                .setOpenableLayout(drawerLayout)
                .build();

        navigationView.getMenu().findItem(R.id.btn_menu_nav_sair).setOnMenuItemClickListener(item -> sair());
    }

    private boolean sair() {
        System.out.println("SAIR DO APLICATIVO");
        return true;
    }
}
