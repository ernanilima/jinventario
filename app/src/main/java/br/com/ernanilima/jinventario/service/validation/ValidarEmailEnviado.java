package br.com.ernanilima.jinventario.service.validation;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ValidarEmailEnviado {
    // https://qastack.com.br/programming/1555262/calculating-the-difference-between-two-java-date-instances

    /** Verifica se e-mail pode ser enviado novamente
     * @param dateGravadoNoBancoDeDados Date - date gravado no bando greendao
     * @return boolean - true se puder enviar o e-mail */
    public static boolean isEnviarNovoEmail(Date dateGravadoNoBancoDeDados) {
        // retorna true apenas quando o tempo para enviar for inferior ou igual a '0'
        // se for maior que '0' o e-mail nao pode ser enviado
        return getTempoParaNovoEmail(dateGravadoNoBancoDeDados) <= 0;

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
     * @param dateGravadoNoBancoDeDados Date - date gravado no bando greendao
     * @return long - tempo faltante para que o usuario possa enviar um novo e-mail */
    public static long getTempoParaNovoEmail(Date dateGravadoNoBancoDeDados) {
        // minutos aceitos para enviar um novo e-mail
        int minutosEnviarNovoEmail = 10;

        // diferanca entre data/hora atual e data/hora gravado no banco de dados greendao
        // diferenca em milissegundos
        long diferencaMilissegundos = System.currentTimeMillis() - dateGravadoNoBancoDeDados.getTime();

        // converte a diferenca gerada em milissegundos e retorna a conversao como minutos
        // retorna o tenpo que o usuario deve esperar para enviar um novo e-mail
        return minutosEnviarNovoEmail - TimeUnit.MINUTES.convert(diferencaMilissegundos, TimeUnit.MILLISECONDS);
    }
}
