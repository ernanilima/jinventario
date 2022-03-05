package br.com.ernanilima.jinventario.data.result;

/**
 * Usada para receber os resultados
 */
interface IResult {
    fun setResult(iResult: IResultType)
    fun setResultFirebaseError(error: String)
}