package com.neqabty.yodawy.modules.address.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.yodawy.core.utils.AppUtils
import com.neqabty.yodawy.core.utils.Resource
import com.neqabty.yodawy.modules.address.domain.entity.CourseEntity
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
class HomeViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) :
    ViewModel() {
    val user = MutableLiveData<Resource<UserEntity>>()
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
            } catch (exception: Throwable) {
                user.postValue(
                    Resource.error(
                        data = null,
                        message = AppUtils().handleError(exception)
                    )
                )
            }
        }
    }
}