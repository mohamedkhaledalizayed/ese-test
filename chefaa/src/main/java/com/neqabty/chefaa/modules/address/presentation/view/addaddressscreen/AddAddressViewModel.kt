package com.neqabty.chefaa.modules.address.presentation.view.addaddressscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
//    private val addAddressUseCase: AddAddressUseCase
    ) : ViewModel() {
    val data = MutableLiveData<String>()
//    val errorMessage = MutableStateFlow("")
//    fun addAddress(params: AddAddressUseCaseParams) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                addAddressUseCase.build(params).collect {
//                    data.postValue(it)
//                }
//            } catch (e:Throwable){
//                errorMessage.emit(e.toString())
//            }
//        }
//    }
}

