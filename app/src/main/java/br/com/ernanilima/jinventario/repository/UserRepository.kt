package br.com.ernanilima.jinventario.repository

import br.com.ernanilima.jinventario.model.User

interface UserRepository {
    fun insert(user: User)
}