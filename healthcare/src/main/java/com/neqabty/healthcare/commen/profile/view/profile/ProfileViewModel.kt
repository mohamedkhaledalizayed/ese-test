package com.neqabty.healthcare.commen.profile.view.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.commen.profile.domain.entity.licencestatus.LicenceStatusEntity
import com.neqabty.healthcare.commen.profile.domain.entity.membershipcardstatus.CardStatusEntity
import com.neqabty.healthcare.commen.profile.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.commen.profile.domain.interactors.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileUseCase: ProfileUseCase): ViewModel() {

    val user = MutableLiveData<Resource<ProfileEntity>>()
    fun getUserProfile(token:String) {
        user.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileUseCase.build(token).collect {
                    user.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                user.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val cardStatus = MutableLiveData<Resource<CardStatusEntity>>()
    fun membershipCardStatus() {
        cardStatus.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileUseCase.build().collect {
                    cardStatus.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                cardStatus.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val licenceStatus = MutableLiveData<Resource<LicenceStatusEntity>>()
    fun getLicenseStatus() {
        licenceStatus.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileUseCase.licenseStatus().collect {
                    licenceStatus.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                licenceStatus.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    private fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "الرجاء تجديد ترخيص الوزارة"
                }
                401 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                403 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                404 -> {
                    "404"
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
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