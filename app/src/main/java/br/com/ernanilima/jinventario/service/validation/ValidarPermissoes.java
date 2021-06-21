package br.com.ernanilima.jinventario.service.validation;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.ernanilima.jinventario.interfaces.IResultadoPermissao;

public class ValidarPermissoes {

    private static ValidarPermissoes VALIDAR_PERMISSOES;
    private Fragment fragment;
    private String[] permissoes;
    private IResultadoPermissao iResultadoPermissao;
    private ActivityResultLauncher<String[]> abrirParaObterResultado;

    /** Cria nova validacao de permissoes
     * @return ValidarPermissoes */
    public static ValidarPermissoes novaValidacao() {
        VALIDAR_PERMISSOES = new ValidarPermissoes();
        return VALIDAR_PERMISSOES;
    }

    /** Defina o fragmento
     * Exemplo ValidarPermissoes.novaValidacao().setFragment(this);
     * @param fragment Fragment - fragment da tela que vai usar essa classe
     * @return ValidarPermissoes */
    public ValidarPermissoes setFragment(Fragment fragment) {
        this.fragment = fragment;
        construirExibirObterResultado();
        return this;
    }

    /** @param permissoes String[] - permissoes solicitadas
     * @return ValidarPermissoes */
    public ValidarPermissoes setPermissoes(String[] permissoes) {
        this.permissoes = permissoes;
        return this;
    }

    /** Usado antes do metodo {@link ValidarPermissoes#validarPermissoes()}
     * Exemplo ValidarPermissoes.novaValidacao.setReceberResposta(this).validarPermissoes();
     * @param iResultadoPermissao IResultadoPermissao - onde o resultado sera exibido
     * @return ValidarPermissoes */
    public ValidarPermissoes setReceberResposta(IResultadoPermissao iResultadoPermissao) {
        this.iResultadoPermissao = iResultadoPermissao;
        return this;
    }

    /** Constroi um callback para exibir e obter o resultado
     * Substitui startActivityForResult e onActivityResult */
    private void construirExibirObterResultado() {
        // https://developer.android.com/training/basics/intents/result
        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
        abrirParaObterResultado = fragment.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                permissions -> {
                    System.out.println("O RESULTADO DAS PERMISSOES EH " + permissions);
                    iResultadoPermissao.setResultadoPermissao(permissions.containsValue(true));
                });
    }

    /** Exibe o dialog do android para solicitar as permissoes para o aplicativo
     * As mesmas permissoes solicitadas devem estar no manifest.xml */
    public void validarPermissoes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // se a api do aparelho for igual ou maior que 23

            // cria lista de permissoes
            List<String> lstPermissoes = new ArrayList<>();
            for (String permissao : permissoes) {

                // verifica se a permissao ja foi dada
                boolean temPermissao = ContextCompat.checkSelfPermission(fragment.getActivity(), permissao) == PackageManager.PERMISSION_GRANTED;

                // se a permissao nao foi dada, adiciona a permissao na lista
                if (!temPermissao) { lstPermissoes.add(permissao); }
            }

            // verifica se a lista esta vazia, significa que todas as permissoes foram dadas
            if (lstPermissoes.isEmpty()) {
                iResultadoPermissao.setResultadoPermissao(true);
                return;
            }

            // cria uma lista de permissoes com o catalho da lista
            String[] novasPermissoes = new String[lstPermissoes.size()];
            // atribui os dados da lista para a nova string de permissoes
            lstPermissoes.toArray(novasPermissoes);

            abrirParaObterResultado.launch(novasPermissoes);
        }
    }
}
