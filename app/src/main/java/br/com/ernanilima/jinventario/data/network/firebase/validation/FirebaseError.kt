package br.com.ernanilima.jinventario.data.network.firebase.validation

import android.content.Context
import br.com.ernanilima.jinventario.R

object FirebaseError {

    /**
     * Obter a mensagem de erro com base no errorCode retornado pelo firebase
     * @param errorCode String - codigo do erro
     * @param context Context - context do fragment
     * @return String - mensagem do erro
     */
    fun getMessage(errorCode: String, context: Context): String {
        // existem validacoes que nao foram feitas por nao serem para o usuario
        // https://github.com/firebase/FirebaseUI-Android/blob/master/auth/src/main/java/com/firebase/ui/auth/util/FirebaseAuthError.java
        return when (errorCode) {
            "ERROR_USER_DISABLED" -> context.getString(R.string.msg_disabled_user)

            "ERROR_USER_NOT_FOUND" -> context.getString(R.string.msg_user_not_found)

            "ERROR_USER_TOKEN_EXPIRED" -> context.getString(R.string.msg_expired_token)

            "ERROR_INVALID_USER_TOKEN" -> context.getString(R.string.msg_broken_token)

            "ERROR_EMAIL_ALREADY_IN_USE",
            "ERROR_CREDENTIAL_ALREADY_IN_USE",
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> context.getString(R.string.msg_email_already_exists)

            "ERROR_INVALID_CUSTOM_TOKEN",
            "ERROR_CUSTOM_TOKEN_MISMATCH" -> context.getString(R.string.msg_invalid_token)

            "ERROR_INVALID_CREDENTIAL" -> context.getString(R.string.msg_invalid_credential)

            "ERROR_INVALID_EMAIL" -> context.getString(R.string.msg_invalid_email)

            "ERROR_WEAK_PASSWORD",
            "ERROR_WRONG_PASSWORD" -> context.getString(R.string.msg_invalid_password)

            "ERROR_USER_MISMATCH" -> context.getString(R.string.msg_credential_of_another_user)

            "ERROR_REQUIRES_RECENT_LOGIN" -> context.getString(R.string.msg_relogin)

            "ERROR_OPERATION_NOT_ALLOWED" -> context.getString(R.string.msg_operation_not_allowed)

            "ERROR_TOO_MANY_REQUESTS" -> context.getString(R.string.msg_locked_device)

            "ERROR_UNKNOWN" -> context.getString(R.string.msg_unknown_error)

            "FirebaseTooManyRequestsException" -> context.getString(R.string.msg_verification_not_sent)

            else -> errorCode
        }
    }
}