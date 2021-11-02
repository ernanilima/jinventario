package br.com.ernanilima.jinventario.repository.impl

import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.repository.UserRepository
import br.com.ernanilima.jinventario.repository.orm.UserDao
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: UserDao): UserRepository {
    override fun insert(user: User) {
        dao.save(user)
    }
}