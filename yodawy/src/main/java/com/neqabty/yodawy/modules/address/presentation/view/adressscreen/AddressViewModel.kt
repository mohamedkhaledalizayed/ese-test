package com.neqabty.yodawy.modules.address.presentation.view.adressscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
import com.neqabty.yodawy.modules.address.domain.interactors.GetUserUseCase
import com.neqabty.yodawy.modules.address.domain.params.GetUserUseCaseParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) :
    ViewModel() {
    val user = MutableLiveData<UserEntity>()
    val errorMessage = MutableStateFlow("")
    fun getUser(userNumber: String, mobileNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getUserUseCase.build(
                    GetUserUseCaseParams(
                        mobileNumber = mobileNumber,
                        userNumber = userNumber
                    )
                ).collect {
                    user.postValue(it)
                }
            } catch (e:Throwable){
                errorMessage.emit(e.toString())
            }
        }
    }
}

