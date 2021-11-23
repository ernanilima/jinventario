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

import javax.inject.Inject;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.IEsqueceuSenha;
import br.com.ernanilima.jinventario.service.navcontroller.Navigation;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EsqueceuSenhaFragment extends Fragment implements IEsqueceuSenha.IView {

    @Inject IEsqueceuSenha.IPresenter pEsqueceuSenha;
    private TextInputLayout campo_email;
    private AppCompatButton btn_novasenha;
    private TextView link_btn_voltar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inicia o xml
        return inflater.inflate(R.layout.fragment_login_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        // DEFINE PRESENTER DESSA ACTIVITY
//        pEsqueceuSenha = new EsqueceuSenhaPresenter(this);

        // INICIALIZA
        // nome_local = nome_no_xml
        campo_email = view.findViewById(R.id.layout_email);
        btn_novasenha = view.findViewById(R.id.btn_send);
        link_btn_voltar = view.findViewById(R.id.btn_back);

        // ACAO DE BOTOES
        btn_novasenha.setOnClickListener(pEsqueceuSenha::gerarNovaSenha);
//        link_btn_voltar.setOnClickListener(v -> ((Activity) v.getContext()).onBackPressed());
        link_btn_voltar.setOnClickListener(v -> Navigation.Login.Companion.toLoginFragment(this));

    }

    @Override
    public TextInputLayout getCampoEmail() {
        return campo_email;
    }
}
