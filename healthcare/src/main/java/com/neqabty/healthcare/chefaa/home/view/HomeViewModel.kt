package com.neqabty.healthcare.chefaa.home.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.chefaa.home.domain.entities.RegistrationEntity
import com.neqabty.healthcare.chefaa.home.domain.entities.orders.OrdersListEntity
import com.neqabty.healthcare.chefaa.home.domain.usecase.RegisterUseCase
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity
import com.neqabty.healthcare.chefaa.orders.domain.usecases.GetOrdersUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val registerUseCase: RegisterUseCase,
                                        private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {
    val userRegistered = MutableLiveData<Resource<RegistrationEntity>>()

    fun registerUser(phoneNumber:String,userId:String,countryCode:String,nationalId:String,name:String){
        viewModelScope.launch(Dispatchers.IO) {
            userRegistered.postValue(Resource.loading(data = null))
            try {
                registerUseCase.build(phoneNumber,userId,countryCode,nationalId,name).collect {
                    userRegistered.postValue(Resource.success(it))
                }
            }catch (e:Throwable){
                Log.e("error Register",e.toString())
                userRegistered.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val orders = MutableLiveData<Resource<OrdersListEntity>>()
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