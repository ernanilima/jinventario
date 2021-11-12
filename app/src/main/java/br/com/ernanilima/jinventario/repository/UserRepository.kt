package br.com.ernanilima.jinventario.repository

import br.com.ernanilima.jinventario.model.User

interface UserRepository {
    fun insert(user: User)
    fun update(user: User)
    fun findByEmail(email: String): User
}