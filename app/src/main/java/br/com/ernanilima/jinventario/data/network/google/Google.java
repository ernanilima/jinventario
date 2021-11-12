package br.com.ernanilima.jinventario.data.network.google;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAutenticacao;
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAutenticacao;
import br.com.ernanilima.jinventario.data.network.firebase.TipoResultado;
import br.com.ernanilima.jinventario.interfaces.IResultadoFirebase;


public class Google {

    private static Google GOOGLE;
    private Fragment fragmentLogin;
    private ActivityResultLauncher<Intent> abrirParaObterResultado;
    private IResultadoFirebase iResultadoGoogle;
    private IFirebaseAutenticacao iFirebaseAutenticacao;

    /** @return Google - instancia da classe {@link Google} */
    public static Google getInstance() {
        if (GOOGLE == null) {
            GOOGLE = new Google();
        } return GOOGLE;
    }

    /** Servico de login do google
     * @param tokenAplicacao String - token da aplicacao
     * @param activity Activity - activity que inicializou o servico
     * @return GoogleSignInClient */
    public GoogleSignInClient servicoLoginGoogle(String tokenAplicacao, Activity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(tokenAplicacao)
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(activity, gso);
    }

    /** Realiza login com o google
     * Grava login realizado no firebase
     * Exibe resultado do login
     * @param mGoogleSignInClient GoogleSignInClient - servico de login do google
     * @param iResultadoFirebase IResultadoFirebase -  interface que recebera o resultado */
    public void loginGoogle(GoogleSignInClient mGoogleSignInClient, IResultadoFirebase iResultadoFirebase) {
        iResultadoGoogle = iResultadoFirebase;
        iFirebaseAutenticacao = new FirebaseAutenticacao(iResultadoFirebase);
        Intent intent = mGoogleSignInClient.getSignInIntent();
        abrirParaObterResultado.launch(intent);
    }

    /** Defina o fragmento
     * Usado para construir novo startActivityForResult e onActivityResult
     * @param fragmentLogin Fragment - fragment da tela que inicializou o servico de login do google */
    public void setFragmentLogin(Fragment fragmentLogin) {
        this.fragmentLogin = fragmentLogin;
        construirExibirObterResultado();
    }

    /** Constroi um callback para exibir e obter o resultado de login com o google
     * Substitui startActivityForResult e onActivityResult */
    private void construirExibirObterResultado() {
        // https://developer.android.com/training/basics/intents/result
        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
        abrirParaObterResultado = fragmentLogin.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        setUsuarioLoginGoogleRealizado(result.getData());
                    } else {
                        // login cancelado
                        iResultadoGoogle.setResultado(TipoResultado.UNAUTHENTICATED_USER);
                    }
                });
    }

    /** Captura os dados do usuario que realizou o login com o google
     * @param data Intent - data recebido no callback de resultado */
    private void setUsuarioLoginGoogleRealizado(Intent data) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount contaUsuario = task.getResult(ApiException.class);
            iFirebaseAutenticacao.loginUsuarioGoogle(contaUsuario.getIdToken());
        } catch (ApiException e) { e.printStackTrace(); }
    }
}
