package br.com.ernanilima.jinventario;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import br.com.ernanilima.jinventario.interfaces.INomeAparelho;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoMain;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NomeAparelhoActivity extends AppCompatActivity implements INomeAparelho.IView {

    @Inject INomeAparelho.IPresenter pNomeAparelho;
    private TextInputLayout campo_nomeaparelho;
    private AppCompatButton btn_gravar;
    private TextView link_btn_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_device_name);

//        // DEFINE PRESENTER DESSA ACTIVITY
//        pNomeAparelho = new NomeAparelhoPresenter(this);

        // INICIALIZA
        // nome_local = nome_no_xml
        campo_nomeaparelho = findViewById(R.id.layout_device_name);
        btn_gravar = findViewById(R.id.btn_save);
        link_btn_voltar = findViewById(R.id.btn_back);

        // ACAO DE BOTOES
        btn_gravar.setOnClickListener(v -> pNomeAparelho.gravarNomeDoAparelho());
        link_btn_voltar.setOnClickListener(v -> sair());

    }

    @Override
    /** Botao voltar do celular */
    public void onBackPressed() {
        sair();
    }

    @Override
    public TextInputLayout getCampoNomeAparelho() {
        return campo_nomeaparelho;
    }

    /** Botao pode ser voltar, mas ao pressionar, o aplicativo faz logout do usuario */
    private void sair() {
        FirebaseAuth.getInstance().signOut();
        NavegacaoMain.abrirTelaActivityMain(this);
    }
}