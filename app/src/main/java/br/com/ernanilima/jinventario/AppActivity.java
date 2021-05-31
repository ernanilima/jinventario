package br.com.ernanilima.jinventario;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;

public class AppActivity extends AppCompatActivity {

    private AppCompatButton btn_sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        btn_sair = findViewById(R.id.btn_sair);
        btn_sair.setOnClickListener(v -> FirebaseAuth.getInstance().signOut());
    }
}
