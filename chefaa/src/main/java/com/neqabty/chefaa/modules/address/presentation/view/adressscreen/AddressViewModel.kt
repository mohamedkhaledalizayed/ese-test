package com.neqabty.chefaa.modules.address.presentation.view.adressscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.core.utils.Resource
import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import com.neqabty.chefaa.modules.address.domain.usecases.GetAllUserAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val getAllUserAddressUseCase: GetAllUserAddressUseCase) :
    ViewModel() {
    val user = MutableLiveData<Resource<List<AddressEntity>>>()
    val errorMessage = MutableStateFlow("")
    fun getUser(userNumber: String, mobileNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            user.postValue(Resource.loading(data = null))
            try {
                getAllUserAddressUseCase.build(
                    mobileNumber
                ).collect {
                    user.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                user.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }
}

