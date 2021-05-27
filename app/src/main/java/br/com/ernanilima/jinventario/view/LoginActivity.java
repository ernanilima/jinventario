package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.service.navcontroller.Navegacao;

public class LoginActivity extends Fragment {

    private TextInputLayout campo_email, campo_senha;
    private TextView link_btn_esqueceu_senha, link_btn_cadastrar;
    private CheckBox chbx_lembrar_login;
    private AppCompatButton btn_login, btn_login_gmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // INICIALIZA
        // nome_local = nome_no_xml
        campo_email = view.findViewById(R.id.campo_email);
        campo_senha = view.findViewById(R.id.campo_senha);
        link_btn_esqueceu_senha = view.findViewById(R.id.btn_esqueceu_senha);
        link_btn_cadastrar = view.findViewById(R.id.btn_cadastrar);
        chbx_lembrar_login = view.findViewById(R.id.chbx_lembrar_login);
        btn_login = view.findViewById(R.id.btn_login);
        btn_login_gmail = view.findViewById(R.id.btn_login_gmail);

        // ACAO DE BOTOES
        link_btn_esqueceu_senha.setOnClickListener(Navegacao::abrirTelaEsqueceuSenha);
        link_btn_cadastrar.setOnClickListener(Navegacao::abrirTelaCadastrar);

    }
}
