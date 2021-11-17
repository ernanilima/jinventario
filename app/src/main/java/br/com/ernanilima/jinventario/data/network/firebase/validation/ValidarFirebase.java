package br.com.ernanilima.jinventario.data.network.firebase.validation;

import android.content.Context;

import br.com.ernanilima.jinventario.extension.common.snackbar.SnackbarCustom;
import br.com.ernanilima.jinventario.service.constant.MensagensAlerta;

public class ValidarFirebase {
    // existem validacoes que nao foram feitas por nao serem para o usuario
    // https://github.com/firebase/FirebaseUI-Android/blob/master/auth/src/main/java/com/firebase/ui/auth/util/FirebaseAuthError.java

    /** Filtra e exibe mensagem de erro personalizada
     * @param context Context - context da tela onde ocorreu o erro
     * @param codigoErro String - codigo do erro recebido pelo firebase */
    public static void erroFirebase(Context context, String codigoErro) {
        switch (codigoErro) {
            case "ERROR_USER_DISABLED":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.USUARIO_DESATIVADO.getMsg());
                break;
            case "ERROR_USER_NOT_FOUND":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.USUARIO_NAO_ENCONTRADO.getMsg());
                break;
            case "ERROR_USER_TOKEN_EXPIRED":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.TOKEN_EXPIRADO.getMsg());
                break;
            case "ERROR_INVALID_USER_TOKEN":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.TOKEN_QUEBRADO.getMsg());
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.EMAIL_JA_EXISTE.getMsg());
                break;
            case "ERROR_INVALID_CUSTOM_TOKEN":
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.TOKEN_INVALIDO.getMsg());
                break;
            case "ERROR_INVALID_CREDENTIAL":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.CREDENCIAL_INVALIDA.getMsg());
                break;
            case "ERROR_INVALID_EMAIL":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.EMAIL_INVALIDO.getMsg());
                break;
            case "ERROR_WEAK_PASSWORD":
            case "ERROR_WRONG_PASSWORD":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.SENHA_INVALIDA.getMsg());
                break;
            case "ERROR_USER_MISMATCH":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.CREDENCIAL_OUTRO_USUARIO.getMsg());
                break;
            case "ERROR_REQUIRES_RECENT_LOGIN":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.REFAZER_LOGIN.getMsg());
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.OPERACAO_NAO_PERMITIDA.getMsg());
                break;
            case "ERROR_TOO_MANY_REQUESTS":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.DISPOSITIVO_BLOQUEADO.getMsg());
                break;
            case "ERROR_UNKNOWN":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.ERRO_DESCONHECIDO.getMsg());
                break;
            case "FirebaseTooManyRequestsException":
                SnackbarCustom.INSTANCE.warning(context, MensagensAlerta.VERIFICACAO_NAO_ENVIAR.getMsg());
                break;

            default:
                SnackbarCustom.INSTANCE.warning(context, codigoErro);
        }
    }
}
