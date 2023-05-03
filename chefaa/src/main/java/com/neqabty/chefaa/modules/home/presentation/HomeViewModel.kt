package com.neqabty.chefaa.modules.home.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.core.utils.Resource
import com.neqabty.chefaa.modules.home.domain.entities.RegistrationEntity
import com.neqabty.chefaa.modules.home.domain.usecase.RegisterUseCase
import com.neqabty.chefaa.modules.home.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.home.domain.usecase.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val registerUseCase: RegisterUseCase,
                                        private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {
    val userRegistered = MutableLiveData<RegistrationEntity>()

    fun registerUser(phoneNumber:String,userId:String,countryCode:String,nationalId:String,name:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                registerUseCase.build(phoneNumber,userId,countryCode,nationalId,name).collect {
                    userRegistered.postValue(it)
                }
            }catch (e:Throwable){
                Log.e("error Register",e.toString())
                userRegistered.postValue(RegistrationEntity(false, ""))
            }
        }
    }

    val orders = MutableLiveData<Resource<List<OrderEntity>>>()
    fun getOrders(mobileNumber:String,pageSize:Int,pageNumber:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orders.postValue(Resource.loading(data = null))
            try {
                getOrdersUseCase.build(mobileNumber, pageSize, pageNumber).collect {
                    orders.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                Log.e("Ordersssss",exception.toString())
                orders.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

}