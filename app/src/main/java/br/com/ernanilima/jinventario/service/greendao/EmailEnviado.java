package br.com.ernanilima.jinventario.service.greendao;

import br.com.ernanilima.jinventario.model.EmailVerificacao;
import br.com.ernanilima.jinventario.model.EmailVerificacaoDao;

public class EmailEnviado {

    private static EmailEnviado EMAIL;

    /** Obtem uma instancia de email enviado
     * @return EmailEnviado - instancia da classe {@link EmailEnviado} */
    public static EmailEnviado getInstance() {
        if (EMAIL == null) {
            EMAIL = new EmailEnviado();
        } return EMAIL;
    }

    /** Model de EmailVerificacao novo ou do banco greendao
     * @param email String - e-mail para realizar busca
     * @param dEmailVerificacao EmailVerificacaoDao - DAO para busca
     * @return EmailVerificacao - se existir no banco greendao, retorna a busca, caso nao exista, retorna um novo */
    public EmailVerificacao getEmailVerificacao(String email, EmailVerificacaoDao dEmailVerificacao) {
        // realiza busca no banco greendao para verificar se e-mail ja existe
        EmailVerificacao dbEmailVerificacao = dEmailVerificacao.queryBuilder().where(EmailVerificacaoDao.Properties.Email.eq(email)).unique();

        // cria um novo model
        EmailVerificacao mEmailVerificacao = new EmailVerificacao();

        // verifica se busca no banco nao eh null
        if (dbEmailVerificacao != null) {
            // se nao for null, atribui o model da busca no model criado
            mEmailVerificacao = dbEmailVerificacao;
        }

        // se o if for null ou nao, atribui o e-mail recebido no parametro
        mEmailVerificacao.setEmail(email);

        // retorna o model criado que pode ou nao conter os dados buscados do greendao
        return mEmailVerificacao;
    }
}
