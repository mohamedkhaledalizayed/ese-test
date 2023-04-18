package com.neqabty.healthcare.chefaa.address.presentation.view.adressscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
import com.neqabty.healthcare.chefaa.address.domain.usecases.GetAllUserAddressUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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

