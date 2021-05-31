package br.com.ernanilima.jinventario.firebase.validation;

import android.content.Context;

import br.com.ernanilima.jinventario.view.toast.ToastPersonalizado;

public class ValidarFirebase {

    /** Filtra e exibe mensagem de erro personalizada
     * @param context Context - context da tela onde ocorreu o erro
     * @param codigoErro String - codigo do erro recebido pelo firebase */
    public static void erroFirebase(Context context, String codigoErro) {
        switch (codigoErro) {
            case "ERROR_USER_DISABLED":
                ToastPersonalizado.erro(context, "Usuário desativado.");
                break;
            case "ERROR_USER_NOT_FOUND":
                ToastPersonalizado.erro(context, "Usuário não encontrado.");
                break;
            case "ERROR_USER_TOKEN_EXPIRED":
                ToastPersonalizado.erro(context, "Token do usuário está expirado.");
                break;
            case "ERROR_INVALID_USER_TOKEN":
                ToastPersonalizado.erro(context, "Token quebrado, erro no app.");
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                ToastPersonalizado.erro(context, "E-mail já existe.");
                break;
            case "ERROR_INVALID_CUSTOM_TOKEN":
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                ToastPersonalizado.erro(context, "Token personalizado inválido.");
                break;
            case "ERROR_INVALID_CREDENTIAL":
                ToastPersonalizado.erro(context, "Credencial inválida.");
                break;
            case "ERROR_INVALID_EMAIL":
                ToastPersonalizado.erro(context, "E-mail inválido.");
                break;
            case "ERROR_WEAK_PASSWORD":
            case "ERROR_WRONG_PASSWORD":
                ToastPersonalizado.erro(context, "Senha inválida.");
                break;
            case "ERROR_USER_MISMATCH":
                ToastPersonalizado.erro(context, "Credenciais não são do usuário atual.");
                break;
            case "ERROR_REQUIRES_RECENT_LOGIN":
                ToastPersonalizado.erro(context, "Esta operação é confidencial e requer autenticação recente. Faça login novamente antes de tentar novamente esta solicitação.");
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                ToastPersonalizado.erro(context, "Operação não permitida no console do firebase.");
                break;
            case "FirebaseTooManyRequestsException":
                ToastPersonalizado.erro(context, "E-mail de verificação não pode ser enviado, tente mais tarde");
                break;

            default:
                ToastPersonalizado.erro(context, codigoErro);
        }
    }
}
