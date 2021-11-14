package com.neqabty.yodawy.modules.address.presentation.view.adressscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.yodawy.core.utils.Resource
import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
import com.neqabty.yodawy.modules.address.domain.interactors.GetUserUseCase
import com.neqabty.yodawy.modules.address.domain.params.GetUserUseCaseParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) :
    ViewModel() {
    val user = MutableLiveData<Resource<UserEntity>>()
    val errorMessage = MutableStateFlow("")
    fun getUser(userNumber: String, mobileNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            user.postValue(Resource.loading(data = null))
            try {
                getUserUseCase.build(
                    GetUserUseCaseParams(
                        mobileNumber = mobileNumber,
                        userNumber = userNumber
                    )
                ).collect {
                    user.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                user.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    private fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "Wrong Username or Password"
                }
                401 -> {
                    "401"
                }
                403 -> {
                    "You should login"
                }
                404 -> {
                    "Not Found"
                }
                500 -> {
                    "Something went wrong"
                }
                else -> {
                    throwable.message!!
                }
            }
        } else {
            throwable.message!!
        }
    }
}

