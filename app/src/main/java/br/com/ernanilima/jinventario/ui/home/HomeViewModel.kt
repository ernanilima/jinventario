package br.com.ernanilima.jinventario.ui.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseDatabase
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal
import br.com.ernanilima.jinventario.model.StockCount
import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.repository.StockCountRepository
import br.com.ernanilima.jinventario.repository.UserRepository
import br.com.ernanilima.jinventario.ui.AppActivity
import br.com.ernanilima.jinventario.view.ContagemFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserRepository,
    private val stockCountDao: StockCountRepository,
    private var iFirebaseAuth: IFirebaseAuth
): ViewModel(), IHome.IViewModel {

    private var _user: User? = null
    val user get() = _user!!

    private var _arguments: Bundle? = null
    val arguments get() = _arguments!!

    private var _stockCount: StockCount? = null
    val stockCount get() = _stockCount!!

    private val _countResult = MutableLiveData<IResultType>()
    val countResult: LiveData<IResultType> = _countResult

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAuth = FirebaseAuth()
        this._user = userDao.findByEmail(iFirebaseAuth.getUserEmail())
        AppActivity.user = user
    }

    /**
     * Criar nova contagem de estoque
     */
    override fun newCount() {
        val currentDate = Date(System.currentTimeMillis())
        val stockCount = StockCount(null, currentDate, currentDate)

        stockCountDao.insert(stockCount) // banco de dados local
        FirebaseDatabase.saveStockCount(stockCount) // banco de dados no firebase

        val bundle = Bundle()
        bundle.putLong(ContagemFragment.CODIGO_CONTAGEM, stockCount.id)
        _arguments = bundle
        _countResult.postValue(ResultTypeLocal.NEW_STOCK_COUNT)
    }

    /**
     * Lista de contagens para popular o recycler view
     * @return List<StockCount>
     */
    override fun listStockCount(): List<StockCount> {
        return stockCountDao.findAll()
    }

    /**
     * Alterar ao clicar no icone do recycler view
     * @param stockCount StockCount
     */
    override fun updateCount(stockCount: StockCount) {
        val bundle = Bundle()
        bundle.putLong(ContagemFragment.CODIGO_CONTAGEM, stockCount.id)
        _arguments = bundle
        _countResult.postValue(ResultTypeLocal.UPDATE_STOCK_COUNT)
    }

    /**
     * Apagar ao deslizar o item
     * @param stockCount StockCount
     */
    override fun deleteCount(stockCount: StockCount) {
        this._stockCount = stockCount
        _countResult.postValue(ResultTypeLocal.DELETE_STOCK_COUNT)
    }

    /**
     * Apagar ao confirmar o dialog
     */
    override fun deleteCount() {
        stockCountDao.delete(stockCount)
        FirebaseDatabase.deleteStockCount(stockCount)
    }
}