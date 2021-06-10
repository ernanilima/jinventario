package br.com.ernanilima.jinventario.service.component;

import android.content.Intent;
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
     * @param codigoContagem long - codigo da contagem
     * @param lsItensContagem List<ItemContagem> - lista de itens para compor o arquivo */
    public static void csv(IContagem.IView vContagem, long codigoContagem, List<ItemContagem> lsItensContagem) {
        // gera o arquivo com o codigo da contagem
        File arquivo = new File(vContagem.requireParentFragment().getContext().getFilesDir(), "contagem_" + codigoContagem + ".csv");

        if (criarArquivoCsv(arquivo, lsItensContagem)) {
            Uri uri = FileProvider.getUriForFile(vContagem.requireParentFragment().getContext(), "br.com.ernanilima.jinventario.provider", arquivo);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);

            // tipo de arquivo que sera compartilhado
            intent.setType("application/csv");

            // descricao que sera exibida antes de selecionar a aplicacao para compartilhar
            vContagem.requireParentFragment().getActivity().startActivity(Intent.createChooser(intent, "Enviar " + arquivo.getName()));
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
