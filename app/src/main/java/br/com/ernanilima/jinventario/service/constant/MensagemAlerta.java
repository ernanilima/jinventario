package br.com.ernanilima.jinventario.service.constant;

public class MensagemAlerta {

    public static final String EMAIL_INVALIDO = "Informe um e-mail válido";
    public static final String SENHA_INVALIDA = "Informe uma senha válida";
    public static final String SENHAS_NAO_COMBINAM = "Senhas não combinam";

    /** @param qtdMinima int - quantidade minima de caractere(s)
     * @return String - mensagem personalizada com o parametro */
    public static String getMsgMinCaracteres(int qtdMinima) {
        return "Necessário informar no mínimo " + qtdMinima + " caractere(s)";
    }
}
