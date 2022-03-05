package br.com.ernanilima.jinventario.data.result

import java.lang.IllegalArgumentException

/**
 * Resultados para erros do firebase - [br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth]
 */
enum class ResultTypeFirebaseError: IResultType {
    ERROR_USER_DISABLED,
    ERROR_USER_NOT_FOUND,
    ERROR_USER_TOKEN_EXPIRED,
    ERROR_INVALID_USER_TOKEN,
    ERROR_EMAIL_ALREADY_IN_USE,
    ERROR_CREDENTIAL_ALREADY_IN_USE,
    ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL,
    ERROR_INVALID_CUSTOM_TOKEN,
    ERROR_CUSTOM_TOKEN_MISMATCH,
    ERROR_INVALID_CREDENTIAL,
    ERROR_INVALID_EMAIL,
    ERROR_WEAK_PASSWORD,
    ERROR_WRONG_PASSWORD,
    ERROR_USER_MISMATCH,
    ERROR_REQUIRES_RECENT_LOGIN,
    ERROR_OPERATION_NOT_ALLOWED,
    ERROR_TOO_MANY_REQUESTS,
    ERROR_UNKNOWN,
    FirebaseTooManyRequestsException,
    NULL;

    open fun toEnum(error: String?): ResultTypeFirebaseError {
        if (error == null) return NULL
        for (x in values())
            if (error == x.name) return x
        throw IllegalArgumentException("Inválido")
    }
}