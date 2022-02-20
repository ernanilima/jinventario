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
    DELETE_STOCK_COUNT,
    LIST_STOCK_COUNT_ITEM,
    NEW_STOCK_COUNT_ITEM,
    UPDATE_STOCK_COUNT_ITEM,
    DELETE_STOCK_COUNT_ITEM,
    SAVED_SETTINGS,
    REFRESH_SETTINGS
}