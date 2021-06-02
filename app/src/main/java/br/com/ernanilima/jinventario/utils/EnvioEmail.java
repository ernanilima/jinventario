package br.com.ernanilima.jinventario.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EnvioEmail {

    /** Verifica se e-mail pode ser enviado novamente
     * @param dataGravadaNoBancoDeDados Date - date gravado no bando
     * @return boolean - true se puder enviar o e-mail */
    public static boolean isEnviarNovoEmail(Date dataGravadaNoBancoDeDados) {
        // retorna true apenas quando o tempo para enviar for inferior ou igual a '0'
        // se for maior que '0' o e-mail nao pode ser enviado
        return getTempoParaNovoEmail(dataGravadaNoBancoDeDados) <= 0;

        //// ABAIXO SERVE COMO REFERENCIA PARA OUTRA FORMA
        //// minutos para enviar um novo e-mail
        //int minutosEnviarNovoEmail = 2;
        //// obtem data e hora atual
        //Calendar dataHoraAtual = Calendar.getInstance();
        //// altera hora atual, registra hora atual menos os minutos para enviar novo e-mail
        //dataHoraAtual.add(Calendar.MINUTE, -minutosEnviarNovoEmail);
        //// retorna o resultado da comparacao
        //return dataGravadaNoBancoDeDados.before(dataHoraAtual.getTime());
    }

    /** Exibe quantos minutos faltam para que o usuario possa enviar um novo e-mail
     * @param dataGravadaNoBancoDeDados Date - date gravado no bando
     * @return long - tempo faltante para que o usuario possa enviar um novo e-mail */
    public static long getTempoParaNovoEmail(Date dataGravadaNoBancoDeDados) {
        // minutos para enviar um novo e-mail
        int minutosEnviarNovoEmail = 10;

        // diferanca entre data/hora atual e data/hora gravado no banco de dados
        // diferenca em milissegundos
        long diferencaMilissegundos = System.currentTimeMillis() - dataGravadaNoBancoDeDados.getTime();

        // converte a diferenca gerada em milissegundos e retorna como minutos
        // retorna o tenpo que o usuario deve esperar para enviar um novo e-mail
        return minutosEnviarNovoEmail - TimeUnit.MINUTES.convert(diferencaMilissegundos, TimeUnit.MILLISECONDS);
    }
}
