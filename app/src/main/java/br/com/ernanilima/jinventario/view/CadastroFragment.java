package br.com.ernanilima.jinventario.view;

import android.app.Activity;
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
import br.com.ernanilima.jinventario.interfaces.ICadastro;
import br.com.ernanilima.jinventario.presenter.CadastroPresenter;

public class CadastroFragment extends Fragment implements ICadastro.CadastroView {

    private ICadastro.CadastroPresenter pCadastro;
    private TextInputLayout campo_email, campo_senha1, campo_senha2;
    private AppCompatButton btn_cadastrar;
    private TextView link_btn_voltar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_login_cadastro, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // DEFINE PRESENTER DESSA ACTIVITY
        pCadastro = new CadastroPresenter(this);

        // INICIALIZA
        // nome_local = nome_no_xml
        campo_email = view.findViewById(R.id.campo_email);
        campo_senha1 = view.findViewById(R.id.campo_senha1);
        campo_senha2 = view.findViewById(R.id.campo_senha2);
        btn_cadastrar = view.findViewById(R.id.btn_cadastrar);
        link_btn_voltar = view.findViewById(R.id.btn_voltar);

        // ACAO DE BOTOES
        campo_senha2.getEditText().setOnClickListener(pCadastro::cadastrar); // botao de teclado
        btn_cadastrar.setOnClickListener(pCadastro::cadastrar);
        link_btn_voltar.setOnClickListener(v -> ((Activity) v.getContext()).onBackPressed());

    }

    @Override
    public TextInputLayout getCampoEmail() {
        return campo_email;
    }

    @Override
    public TextInputLayout getCampoSenha1() {
        return campo_senha1;
    }

    @Override
    public TextInputLayout getCampoSenha2() {
        return campo_senha2;
    }
}
