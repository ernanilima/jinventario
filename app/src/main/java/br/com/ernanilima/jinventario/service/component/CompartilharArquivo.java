package br.com.ernanilima.jinventario.service.component;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.ernanilima.jinventario.interfaces.IContagem;
import br.com.ernanilima.jinventario.model.ItemContagem;

public class CompartilharArquivo {

    /** Cria o arquivo de compartilhamento com o codigo da contadem, arquivo em csv
     * @param vContagem IContagem.IView - view que solicitou o compartilhamento
     * @param nomeAparelho String - nome do aparelho para compor o nome do arquivo
     * @param codigoContagem long - codigo da contagem
     * @param lsItensContagem List<ItemContagem> - lista de itens para compor o arquivo  */
    public static void csv(IContagem.IView vContagem, String nomeAparelho, long codigoContagem, List<ItemContagem> lsItensContagem) {
        // gera o arquivo com o codigo da contagem
        File arquivo = new File(vContagem.requireParentFragment().getContext().getFilesDir(), "contagem_" + nomeAparelho + "_" + codigoContagem + ".csv");

        if (criarArquivoCsv(arquivo, lsItensContagem)) {
            Uri uri = FileProvider.getUriForFile(vContagem.requireParentFragment().getContext(), "br.com.ernanilima.jinventario.provider", arquivo);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            // tipo de arquivo que sera compartilhado
            intent.setType("application/csv");

            // descricao que sera exibida antes de selecionar a aplicacao para compartilhar
            Intent novaIntent = Intent.createChooser(intent, "Enviar " + arquivo.getName());

            List<ResolveInfo> resInfoList = vContagem.requireParentFragment().getActivity().getPackageManager().queryIntentActivities(novaIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                vContagem.requireParentFragment().getActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            vContagem.requireParentFragment().getActivity().startActivity(novaIntent);
        }
    }

    /** Gera e grava os dados dentro do arquivo csv
     * @param arquivo File - arquido onde os dados seram armazenados
     * @param lsItensContagem List<ItemContagem> - lista de itens para compor o arquivo
     * @return boolean - true se dados gravados no arquivo */
    private static boolean criarArquivoCsv(File arquivo, List<ItemContagem> lsItensContagem) {
        try {
            if (lsItensContagem != null && lsItensContagem.size() != 0) {
                if (!arquivo.exists()) {
                    // cria o arquivo caso nao exista
                    arquivo.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(arquivo);
                // cabecalho do arquivo
                fileWriter.append("codigo de barras;quantidade de caixas;quantidade por caixa;quantidade total de itens\n");
                for (ItemContagem itemContagem : lsItensContagem) {
                    // conteudo do arquivo
                    fileWriter.append(itemContagem.getCodigoBarras() + ";" + itemContagem.getQtdDeCaixas() + ";" +
                            itemContagem.getQtdPorCaixa() + ";" + itemContagem.getQtdTotal() + "\n");
                }
                fileWriter.close();
                return true;
            }
        } catch (IOException e) {e.printStackTrace();}
        return false;
    }
}
