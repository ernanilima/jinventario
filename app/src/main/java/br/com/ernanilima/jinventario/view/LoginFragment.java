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

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import br.com.ernanilima.jinventario.R;
import br.com.ernanilima.jinventario.interfaces.ILogin;
import br.com.ernanilima.jinventario.service.navcontroller.NavegacaoMain;
import br.com.ernanilima.jinventario.service.social.Google;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment implements ILogin.IFragment {

    @Inject ILogin.IPresenter pLogin;
    private TextInputLayout campo_email, campo_senha;
    private TextView link_btn_esqueceu_senha, link_btn_cadastrar;
    private AppCompatButton btn_login, btn_login_gmail;
    private GoogleSignInClient mGoogleSignInClient;

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
        btn_login = view.findViewById(R.id.btn_login);
        btn_login_gmail = view.findViewById(R.id.btn_login_gmail);

        // ACAO DE BOTOES
        campo_senha.getEditText().setOnClickListener(v -> pLogin.login()); // botao de teclado
        btn_login.setOnClickListener(v -> pLogin.login());
        btn_login_gmail.setOnClickListener(v -> pLogin.loginGmail());
        link_btn_esqueceu_senha.setOnClickListener(NavegacaoMain::abrirTelaEsqueceuSenha);
        link_btn_cadastrar.setOnClickListener(NavegacaoMain::abrirTelaCadastrar);

        // GOOGLE
        mGoogleSignInClient = Google.getInstance().servicoLoginGoogle(getString(R.string.default_web_client_id), getActivity());
        Google.getInstance().setFragmentLogin(this);

    }

    @Override
    /** Usado principalmente para verificar se existe um usuario autenticado */
    public void onStart() {
        super.onStart();
        pLogin.verificarSeUsuarioAutenticado();
    }

    @Override
    public TextInputLayout getCampoEmail() {
        return campo_email;
    }

    @Override
    public TextInputLayout getCampoSenha() {
        return campo_senha;
    }

    @Override
    public GoogleSignInClient getServicoLoginGoogle() {
        return mGoogleSignInClient;
    }
}
