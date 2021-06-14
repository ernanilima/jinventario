package br.com.ernanilima.jinventario.service.constant;

public enum MensagensAlerta {

    EMAIL_INVALIDO("Informe um e-mail válido"),
    SENHA_INVALIDA("Informe uma senha válida"),
    SENHAS_NAO_COMBINAM("Senhas não combinam"),
    USUARIO_DESATIVADO("Usuário desativado."),
    USUARIO_NAO_ENCONTRADO("Usuário não encontrado."),
    TOKEN_EXPIRADO("Token do usuário está expirado."),
    TOKEN_QUEBRADO("Token quebrado, erro no app."),
    EMAIL_JA_EXISTE("E-mail já existe."),
    TOKEN_INVALIDO("Token personalizado inválido."),
    CREDENCIAL_INVALIDA("Credencial inválida."),
    CREDENCIAL_OUTRO_USUARIO("Credenciais não são do usuário atual."),
    REFAZER_LOGIN("Esta operação é confidencial e requer autenticação recente. Faça login novamente antes de tentar novamente esta solicitação."),
    OPERACAO_NAO_PERMITIDA("Operação não permitida. Ative o método no console do Firebase."),
    VERIFICACAO_NAO_ENVIAR("E-mail de verificação não pode ser enviado, tente mais tarde"),
    DISPOSITIVO_BLOQUEADO("Bloqueamos todas as solicitações deste dispositivo devido a atividade incomum."),
    ERRO_DESCONHECIDO("Ocorreu um erro desconhecido."),
    USUARIO_CADASTRADO("Usuário cadastrado, foi enviado uma verificação para seu e-mail."),
    EMAIL_NOVA_SENHA_ENVIADA("As informações para \"Nova Senha\" foram enviadas no e-mail"),
    EMAIL_VERIFICACAO_ENVIADO("Novo e-mail de verificação foi enviado"),
    CODBARRAS_INVALIDO("Código de barras inválido"),
    QUANTIDADE_DE_CAIXA_INVALIDO("Qtd de caixa(s) inválido"),
    QUANTIDADE_POR_CAIXA_INVALIDO("Qtd por caixa(s) inválido"),
    NOME_APARELHO_INVALIDO("Informe um nome válido para o aparelho"),
    COMPARTILHAMENTO_VAZIO("Nenhum item para compartilhar"),
    SEM_INTERNET("Aparelho sem internet"),
    POLITICA_PRIVACIDADE("Leia e aceita a política de privacidade");

    private final String msg;

    MensagensAlerta(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    /** @param qtdMinima int - quantidade minima de caractere(s)
     * @return String - mensagem personalizada com o parametro */
    public static String getMsgMinCaracteres(int qtdMinima) {
        return "Necessário informar no mínimo " + qtdMinima + " caractere(s)";
    }

    /** @param tempoDeEspera long - tempo para aguardar
     * @return String - mensagem personalizada com o parametro */
    public static String getMsgTempoEsperaEmail(long tempoDeEspera) {
        return "Aguarde " + tempoDeEspera + " minuto(s) para enviar o e-mail novamente";
    }
}
