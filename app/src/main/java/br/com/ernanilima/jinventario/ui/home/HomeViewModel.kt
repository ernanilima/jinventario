package br.com.ernanilima.jinventario.ui.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseDatabase
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.model.IModel
import br.com.ernanilima.jinventario.model.StockCount
import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.repository.StockCountRepository
import br.com.ernanilima.jinventario.repository.UserRepository
import br.com.ernanilima.jinventario.ui.AppActivity
import br.com.ernanilima.jinventario.view.ContagemFragment
import br.com.ernanilima.jinventario.view.dialog.TipoResultado
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserRepository,
    private val stockCountDao: StockCountRepository,
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
        AppActivity.user = user
    }

    override fun newCount() {
        val currentDate = Date(System.currentTimeMillis())
        val stockCount = StockCount(null, currentDate, currentDate)

        stockCountDao.insert(stockCount)
        FirebaseDatabase.saveStockCount(stockCount)

        val bundle = Bundle()
        bundle.putLong(ContagemFragment.CODIGO_CONTAGEM, stockCount.id)
        _arguments.postValue(bundle)
    }

    override fun listStockCount(): List<StockCount> {
        return stockCountDao.findAll()
    }

    override fun updateCount(stockCount: StockCount) {
        TODO("Not yet implemented")
    }

    override fun deleteCount(stockCount: StockCount) {
        TODO("Not yet implemented")
    }

    override fun setResultadoDialog(tipoResultado: TipoResultado?, iModel: IModel?) {
        TODO("Not yet implemented")
    }
}