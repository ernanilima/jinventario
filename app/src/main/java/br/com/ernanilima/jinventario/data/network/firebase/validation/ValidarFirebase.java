package br.com.ernanilima.jinventario.data.network.firebase.validation;

import android.content.Context;

public class ValidarFirebase {
    // existem validacoes que nao foram feitas por nao serem para o usuario
    // https://github.com/firebase/FirebaseUI-Android/blob/master/auth/src/main/java/com/firebase/ui/auth/util/FirebaseAuthError.java

    /** Filtra e exibe mensagem de erro personalizada
     * @param context Context - context da tela onde ocorreu o erro
     * @param codigoErro String - codigo do erro recebido pelo firebase */
    public static void erroFirebase(Context context, String codigoErro) {
        switch (codigoErro) {
//            case "ERROR_USER_DISABLED":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_disabled_user));
//                break;
//            case "ERROR_USER_NOT_FOUND":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_user_not_found));
//                break;
//            case "ERROR_USER_TOKEN_EXPIRED":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_expired_token));
//                break;
//            case "ERROR_INVALID_USER_TOKEN":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_broken_token));
//                break;
//            case "ERROR_EMAIL_ALREADY_IN_USE":
//            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
//            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_email_already_exists));
//                break;
//            case "ERROR_INVALID_CUSTOM_TOKEN":
//            case "ERROR_CUSTOM_TOKEN_MISMATCH":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_invalid_token));
//                break;
//            case "ERROR_INVALID_CREDENTIAL":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_invalid_credential));
//                break;
//            case "ERROR_INVALID_EMAIL":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_invalid_email));
//                break;
//            case "ERROR_WEAK_PASSWORD":
//            case "ERROR_WRONG_PASSWORD":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_invalid_password));
//                break;
//            case "ERROR_USER_MISMATCH":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_credential_of_another_user));
//                break;
//            case "ERROR_REQUIRES_RECENT_LOGIN":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_relogin));
//                break;
//            case "ERROR_OPERATION_NOT_ALLOWED":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_operation_not_allowed));
//                break;
//            case "ERROR_TOO_MANY_REQUESTS":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_locked_device));
//                break;
//            case "ERROR_UNKNOWN":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_unknown_error));
//                break;
//            case "FirebaseTooManyRequestsException":
//                SnackbarCustom.INSTANCE.warning(context, binding.root, context.getString(R.string.msg_verification_not_sent));
//                break;
//
//            default:
//                SnackbarCustom.INSTANCE.warning(context, binding.root, codigoErro);
        }
    }
}
