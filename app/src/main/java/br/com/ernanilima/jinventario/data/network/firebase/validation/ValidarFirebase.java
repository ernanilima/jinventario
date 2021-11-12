package br.com.ernanilima.jinventario.data.network.firebase.validation;

import android.content.Context;

import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;
import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado;

public class ValidarFirebase {
    // existem validacoes que nao foram feitas por nao serem para o usuario
    // https://github.com/firebase/FirebaseUI-Android/blob/master/auth/src/main/java/com/firebase/ui/auth/util/FirebaseAuthError.java

    /** Filtra e exibe mensagem de erro personalizada
     * @param context Context - context da tela onde ocorreu o erro
     * @param codigoErro String - codigo do erro recebido pelo firebase */
    public static void erroFirebase(Context context, String codigoErro) {
        switch (codigoErro) {
            case "ERROR_USER_DISABLED":
                ToastPersonalizado.erro(context, MensagensAlerta.USUARIO_DESATIVADO.getMsg());
                break;
            case "ERROR_USER_NOT_FOUND":
                ToastPersonalizado.erro(context, MensagensAlerta.USUARIO_NAO_ENCONTRADO.getMsg());
                break;
            case "ERROR_USER_TOKEN_EXPIRED":
                ToastPersonalizado.erro(context, MensagensAlerta.TOKEN_EXPIRADO.getMsg());
                break;
            case "ERROR_INVALID_USER_TOKEN":
                ToastPersonalizado.erro(context, MensagensAlerta.TOKEN_QUEBRADO.getMsg());
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                ToastPersonalizado.erro(context, MensagensAlerta.EMAIL_JA_EXISTE.getMsg());
                break;
            case "ERROR_INVALID_CUSTOM_TOKEN":
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                ToastPersonalizado.erro(context, MensagensAlerta.TOKEN_INVALIDO.getMsg());
                break;
            case "ERROR_INVALID_CREDENTIAL":
                ToastPersonalizado.erro(context, MensagensAlerta.CREDENCIAL_INVALIDA.getMsg());
                break;
            case "ERROR_INVALID_EMAIL":
                ToastPersonalizado.erro(context, MensagensAlerta.EMAIL_INVALIDO.getMsg());
                break;
            case "ERROR_WEAK_PASSWORD":
            case "ERROR_WRONG_PASSWORD":
                ToastPersonalizado.erro(context, MensagensAlerta.SENHA_INVALIDA.getMsg());
                break;
            case "ERROR_USER_MISMATCH":
                ToastPersonalizado.erro(context, MensagensAlerta.CREDENCIAL_OUTRO_USUARIO.getMsg());
                break;
            case "ERROR_REQUIRES_RECENT_LOGIN":
                ToastPersonalizado.erro(context, MensagensAlerta.REFAZER_LOGIN.getMsg());
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                ToastPersonalizado.erro(context, MensagensAlerta.OPERACAO_NAO_PERMITIDA.getMsg());
                break;
            case "ERROR_TOO_MANY_REQUESTS":
                ToastPersonalizado.erro(context, MensagensAlerta.DISPOSITIVO_BLOQUEADO.getMsg());
                break;
            case "ERROR_UNKNOWN":
                ToastPersonalizado.erro(context, MensagensAlerta.ERRO_DESCONHECIDO.getMsg());
                break;
            case "FirebaseTooManyRequestsException":
                ToastPersonalizado.erro(context, MensagensAlerta.VERIFICACAO_NAO_ENVIAR.getMsg());
                break;

            default:
                ToastPersonalizado.erro(context, codigoErro);
        }
    }
}
