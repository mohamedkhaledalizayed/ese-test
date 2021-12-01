package com.neqabty.yodawy.modules.address.presentation.view.addaddressscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.yodawy.core.utils.AppUtils
import com.neqabty.yodawy.core.utils.Resource
import com.neqabty.yodawy.modules.address.data.model.AddressResponse
import com.neqabty.yodawy.modules.address.data.model.response.addaddress.AddAddressModel
import com.neqabty.yodawy.modules.address.domain.entity.AddressEntity
import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
import com.neqabty.yodawy.modules.address.domain.interactors.AddAddressUseCase
import com.neqabty.yodawy.modules.address.domain.interactors.GetUserUseCase
import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
import com.neqabty.yodawy.modules.address.domain.params.GetUserUseCaseParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(private val addAddressUseCase: AddAddressUseCase) :
    ViewModel() {
    val data = MutableLiveData<Resource<String>>()
    fun addAddress(params: AddAddressUseCaseParams) {
        viewModelScope.launch(Dispatchers.IO) {
            data.postValue(Resource.loading(data = null))
            try {
                addAddressUseCase.build(params).collect {
                    data.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                data.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }
}

