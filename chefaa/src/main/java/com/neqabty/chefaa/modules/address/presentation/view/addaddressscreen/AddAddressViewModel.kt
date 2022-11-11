package com.neqabty.chefaa.modules.address.presentation.view.addaddressscreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.core.utils.Resource
import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import com.neqabty.chefaa.modules.address.domain.usecases.AddUserAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val addAddressUseCase: AddUserAddressUseCase
) : ViewModel() {
    val data = MutableLiveData<Resource<AddressEntity>>()
    fun addAddress(
        apartment: String = "",
        buildingNo: String = "",
        floor: String = "",
        landMark: String = "",
        lat: String = "",
        long: String = "",
        phone: String = "",
        streetName: String = "",
        title: String = "" ) {
        viewModelScope.launch(Dispatchers.IO) {
            data.postValue(Resource.loading(data = null))
            try {
                addAddressUseCase.build(apartment, buildingNo, floor, landMark, lat, long, phone, streetName, title).collect {
                    data.postValue(Resource.success(it))
                }
            } catch (e: Throwable) {
                data.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}

