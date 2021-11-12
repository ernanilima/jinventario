package br.com.ernanilima.jinventario.repository.impl

import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.repository.UserRepository
import br.com.ernanilima.jinventario.repository.orm.UserDao
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: UserDao): UserRepository {

    /**
     * Cadastra um usuario
     * @param user User - usuario para cadastrar
     */
    override fun insert(user: User) {
        user.id = null
        update(user)
    }

    /**
     * Atualiza um usuario
     * @param user User - usuario para atualizar
     */
    override fun update(user: User) {
        dao.save(user)
    }

    /**
     * Busca um usuario pelo e-mail.
     * Se nao encontrar, retorna um usuario com o e-mail do parametro
     * @param email String - e-mail do usuario
     * @return User
     */
    override fun findByEmail(email: String): User {
        // busca usuario no banco local
        var user = dao.queryBuilder().where(UserDao.Properties.Email.eq(email)).unique()

        if (user == null) {
            // cria um usuario para evitar retorno nulo
            // caso o banco local seja apagado, sempre retorna com o e-mail buscado
            user = User()
            user.email = email
        }

        return user
    }
}