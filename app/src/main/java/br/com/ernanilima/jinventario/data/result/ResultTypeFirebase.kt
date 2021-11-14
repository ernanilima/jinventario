package br.com.ernanilima.jinventario.data.result

/**
 * Resultados para o firebase - [br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth]
 */
enum class ResultTypeFirebase: IResultType {
    REGISTRATION_DONE,
    VERIFICATION_EMAIL_SENT,
    EMAIL_NOT_VERIFIED,
    NEW_PASSWORD_EMAIL_SENT,
    LOGIN_DONE,
    FIRST_LOGIN_DONE,
    AUTHENTICATED_USER,
    UNAUTHENTICATED_USER
}