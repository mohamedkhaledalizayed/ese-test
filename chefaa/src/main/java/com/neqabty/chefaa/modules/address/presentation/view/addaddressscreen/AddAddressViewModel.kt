package com.neqabty.chefaa.modules.address.presentation.view.addaddressscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import com.neqabty.chefaa.modules.address.domain.usecases.AddUserAddressUseCase
//import com.neqabty.yodawy.modules.address.data.model.AddressResponse
//import com.neqabty.yodawy.modules.address.data.model.response.addaddress.AddAddressModel
//import com.neqabty.yodawy.modules.address.domain.entity.AddressEntity
//import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
//import com.neqabty.yodawy.modules.address.domain.interactors.AddAddressUseCase
//import com.neqabty.yodawy.modules.address.domain.interactors.GetUserUseCase
//import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
//import com.neqabty.yodawy.modules.address.domain.params.GetUserUseCaseParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val addAddressUseCase: AddUserAddressUseCase
) : ViewModel() {
    val data = MutableLiveData<AddressEntity>()
    val errorMessage = MutableStateFlow("")
    fun addAddress(
        apartment: Int = 0,
        buildingNo: Int = 0,
        floor: Int = 0,
        landMark: String = "",
        lat: String = "",
        long: String = "",
        phone: String = "",
        streetName: String = "",
        title: String = "" ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addAddressUseCase.build(apartment, buildingNo, floor, landMark, lat, long, phone, streetName, title).collect {
                    data.postValue(it)
                }
            } catch (e: Throwable) {
                errorMessage.emit(e.toString())
            }
        }
    }
}

