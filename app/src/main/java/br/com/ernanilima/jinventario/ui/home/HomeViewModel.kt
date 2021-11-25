package br.com.ernanilima.jinventario.ui.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.model.ContagemEstoque
import br.com.ernanilima.jinventario.model.IModel
import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.repository.UserRepository
import br.com.ernanilima.jinventario.view.dialog.TipoResultado
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserRepository,
    private var iFirebaseAuth: IFirebaseAuth
): ViewModel(), IHome.IViewModel {

    var user: User = User()

    private val _arguments = MutableLiveData<Bundle>()
    val arguments: LiveData<Bundle> = _arguments

    private val _countResult = MutableLiveData<Bundle>()
    val countResult: LiveData<Bundle> = _countResult

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAuth = FirebaseAuth()
        this.user = userDao.findByEmail(iFirebaseAuth.getUserEmail())
    }

    override fun newCount() {
        TODO("Not yet implemented")
    }

    override fun listCounts(): List<ContagemEstoque> {
        return ArrayList()
    }

    override fun updateCount(contagemEstoque: ContagemEstoque) {
        TODO("Not yet implemented")
    }

    override fun deleteCount(contagemEstoque: ContagemEstoque) {
        TODO("Not yet implemented")
    }

    override fun setResultadoDialog(tipoResultado: TipoResultado?, iModel: IModel?) {
        TODO("Not yet implemented")
    }
}