package br.com.ernanilima.jinventario.data.result

/**
 * Resultados de validacoes locais
 */
enum class ResultTypeLocal: IResultType {
    WAIT_SEND_VERIFICATION,
    WAIT_SEND_PASSWORD,
    SAVED_DEVICE_NAME,
    NEW_STOCK_COUNT,
    UPDATE_STOCK_COUNT,
    DELETE_STOCK_COUNT
}