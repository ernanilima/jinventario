package br.com.ernanilima.jinventario.firebase;

/** Enum com o tipo de resultado obtido no firebase
 * {@link FirebaseAutenticacao} */
public enum TipoResultado {
    CADASTRO_REALIZADO,
    EMAIL_VERIFICACAO_ENVIADO,
    EMAIL_NAO_VERIFICADO,
    EMAIL_NOVA_SENHA_ENVIADO,
    LOGIN_REALIZADO,
    FIRST_LOGIN,
    AUTHENTICATED_USER,
    UNAUTHENTICATED_USER
}
