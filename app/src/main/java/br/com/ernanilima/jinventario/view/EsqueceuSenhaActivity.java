package br.com.ernanilima.jinventario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import br.com.ernanilima.jinventario.R;

public class EsqueceuSenhaActivity extends Fragment {

    private TextInputLayout campo_email;
    private AppCompatButton btn_novasenha;
    private TextView link_btn_voltar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_login_esqueceu_senha, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // INICIALIZA
        // nome_local = nome_no_xml
        campo_email = view.findViewById(R.id.campo_email);
        btn_novasenha = view.findViewById(R.id.btn_novasenha);
        link_btn_voltar = view.findViewById(R.id.btn_voltar);

    }
}
