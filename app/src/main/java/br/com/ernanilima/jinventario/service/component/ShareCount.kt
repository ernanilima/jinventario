package br.com.ernanilima.jinventario.service.component

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import br.com.ernanilima.jinventario.model.StockCountItem
import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.ui.AppActivity
import java.io.File
import java.io.FileWriter
import java.io.IOException

object ShareCount {

    private val user: User? = AppActivity.user

    /**
     * Cria o arquivo que sera compartilhado
     * @param fragment Fragment - fragment que solicitou o compartilhamento
     * @param idStockCount Long - id da contagem
     * @param listStockCountItem List<StockCountItem> - lista de itens
     */
    fun csv(fragment: Fragment, idStockCount: Long, listStockCountItem: List<StockCountItem>) {
        // gera o arquivo com o codigo da contagem
        val file: File = File(fragment.requireContext().filesDir, "Contagem_${user?.deviceName}_${idStockCount}.csv")

        if (buildFile(file, listStockCountItem)) {
            val uri: Uri = FileProvider.getUriForFile(fragment.requireContext(), "br.com.ernanilima.jinventario.provider", file)
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION

            // tipo de arquivo que sera compartilhado
            intent.type = "application/csv"

            // descricao que sera exibida antes de selecionar a aplicacao para compartilhar
            val newIntent: Intent = Intent.createChooser(intent, "Enviar ${file.name}")
            val resInfoList: List<ResolveInfo> = fragment.requireActivity().packageManager.queryIntentActivities(newIntent, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolveInfo in resInfoList) {
                val packageName: String = resolveInfo.activityInfo.packageName
                fragment.requireActivity().grantUriPermission(packageName, uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            fragment.requireActivity().startActivity(newIntent)
        }
    }

    /**
     * Gera e grava os dados dentro em um arquivo
     * @param file File - arquivo onde os dados seram armazenados
     * @param listStockCountItem List<StockCountItem> - lista de itens para compor o arquivo
     * @return boolean - true se dados forem gravados
     */
    private fun buildFile(file: File, listStockCountItem: List<StockCountItem>): Boolean {
        try {
            if (listStockCountItem.isNotEmpty()) {
                if (!file.exists()) {
                    // cria o arquivo caso nao exista
                    file.createNewFile()
                }
                val fileWriter = FileWriter(file)
                // cabecalho do arquivo
                fileWriter.append("codigo de barras;preco unitario;quantidade de caixas;quantidade por caixa;\n")
                for (stockCountItem in listStockCountItem) {
                    // conteudo do arquivo
                    fileWriter.append(
                        "${stockCountItem.barcode};${stockCountItem.unitPrice};${stockCountItem.numberOfBoxes};${stockCountItem.numberPerBox}\n"
                    )
                }
                fileWriter.close()
                return true
            }
        } catch (e: IOException) { e.printStackTrace() }
        return false
    }
}