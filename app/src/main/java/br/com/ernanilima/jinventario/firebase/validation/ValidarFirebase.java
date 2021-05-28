package br.com.ernanilima.jinventario.firebase.validation;

import android.content.Context;
import android.widget.Toast;

public class ValidarFirebase {

    /** Filtra e exibe mensagem de erro personalizada
     * @param context Context - context da tela onde ocorreu o erro
     * @param codigoErro String - codigo do erro recebido pelo firebase */
    public static void erroCadastro(Context context, String codigoErro) {
        switch (codigoErro) {
            case "ERROR_EMAIL_ALREADY_IN_USE":
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(context, "E-mail já existe.", Toast.LENGTH_LONG).show();
        }
    }
}
