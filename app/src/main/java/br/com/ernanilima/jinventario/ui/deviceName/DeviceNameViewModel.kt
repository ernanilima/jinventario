package br.com.ernanilima.jinventario.ui.deviceName

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ernanilima.jinventario.data.network.firebase.FirebaseAuth
import br.com.ernanilima.jinventario.data.network.firebase.IFirebaseAuth
import br.com.ernanilima.jinventario.data.result.IResultType
import br.com.ernanilima.jinventario.data.result.ResultTypeLocal.SAVED_DEVICE_NAME
import br.com.ernanilima.jinventario.model.User
import br.com.ernanilima.jinventario.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceNameViewModel @Inject constructor(
    private val userDao: UserRepository,
    private var iFirebaseAuth: IFirebaseAuth
): ViewModel(), IDeviceName.IViewModel {

    var user: User = User()

    private val _deviceNameResult = MutableLiveData<IResultType>()
    val deviceNameResult: LiveData<IResultType> = _deviceNameResult

    init {
        // EXECUTA AO INICIAR A CLASSE
        this.iFirebaseAuth = FirebaseAuth()
    }

    override fun saveDeviceName() {
        val user = userDao.findByEmail(iFirebaseAuth.getUserEmail())
        user.deviceName = this.user.deviceName
        userDao.update(user)
        _deviceNameResult.postValue(SAVED_DEVICE_NAME)
    }
}