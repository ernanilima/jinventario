package br.com.ernanilima.jinventario.service.component;

import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth;
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth;
import br.com.ernanilima.jinventario.model.User;
import br.com.ernanilima.jinventario.repository.orm.DaoSession;
import br.com.ernanilima.jinventario.repository.orm.UserDao;

@Deprecated
public class NomeAparelhoAutenticacao {

    private static NomeAparelhoAutenticacao NOME_APARELHO;
    private IFirebaseAuth iFirebaseAuth;
    private User user;
    private DaoSession daoSession;
    private UserDao userDao;

    /** @param daoSession DaoSession - sessao do greendao
     * @return NomeAparelhoAutenticacao - instancia da classe {@link NomeAparelhoAutenticacao} */
    public static NomeAparelhoAutenticacao getInstance(DaoSession daoSession) {
        if (NOME_APARELHO == null) {
            NOME_APARELHO = new NomeAparelhoAutenticacao(daoSession);
        } return NOME_APARELHO;
    }

    /** Construtor
     * @param daoSession DaoSession - sessao do greendao */
    @Deprecated
    public NomeAparelhoAutenticacao(DaoSession daoSession) {
        // GREENDAO
        this.daoSession = daoSession;
        this.userDao = this.daoSession.getUserDao();
        this.iFirebaseAuth = new FirebaseAuth();
    }

    /** @return String - nome do aparelho */
    @Deprecated
    public String getNomeAparelho() {
        user = userDao.queryBuilder().where(UserDao.Properties.Email.eq(iFirebaseAuth.getUserEmail())).unique();

        if (user == null) {
            user = new User();
            user.setEmail(iFirebaseAuth.getUserEmail());
        }

        if (user.getDeviceName() != null) {
            return user.getDeviceName();
        }

        return "NOME NÃO CRIADO";
    }
}
