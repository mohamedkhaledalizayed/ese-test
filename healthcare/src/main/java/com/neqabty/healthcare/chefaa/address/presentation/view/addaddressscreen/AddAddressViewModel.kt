package com.neqabty.healthcare.chefaa.address.presentation.view.addaddressscreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
import com.neqabty.healthcare.chefaa.address.domain.usecases.AddUserAddressUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

