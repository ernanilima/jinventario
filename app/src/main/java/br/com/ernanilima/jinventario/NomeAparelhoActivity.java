package br.com.ernanilima.jinventario;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.interfaces.INomeAparelho;
import br.com.ernanilima.jinventario.presenter.NomeAparelhoPresenter;

public class NomeAparelhoActivity extends AppCompatActivity implements INomeAparelho.IView {

    private INomeAparelho.IPresenter pNomeAparelho;
    private TextInputLayout campo_nomeaparelho;
    private AppCompatButton btn_gravar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nome_aparelho);

        // DEFINE PRESENTER DESSA ACTIVITY
        pNomeAparelho = new NomeAparelhoPresenter(this);

        // INICIALIZA
        // nome_local = nome_no_xml
        campo_nomeaparelho = findViewById(R.id.campo_nomeaparelho);
        btn_gravar = findViewById(R.id.btn_gravar);

        // ACAO DE BOTOES
        btn_gravar.setOnClickListener(v -> pNomeAparelho.gravarNomeDoAparelho());
    }

    @Override
    public TextInputLayout getCampoNomeAparelho() {
        return campo_nomeaparelho;
    }
}