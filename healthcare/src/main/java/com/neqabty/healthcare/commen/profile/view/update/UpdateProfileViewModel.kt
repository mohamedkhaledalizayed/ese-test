package com.neqabty.healthcare.commen.profile.view.update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.commen.profile.domain.entity.MinistryLicenseEntity
import com.neqabty.healthcare.commen.profile.domain.interactors.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(private val profileUseCase: ProfileUseCase): ViewModel() {

    val membershipCard = MutableLiveData<Resource<String>>()
    fun uploadMembershipCard(token:String, mobile: String, address: String, year: Int, photo: MultipartBody.Part?) {
        membershipCard.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileUseCase.build(token, mobile, address, year, photo).collect {
                    membershipCard.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                membershipCard.postValue(Resource.error(data = null, message = handleError(exception)))
            }
        }
    }

    val ministryLicense = MutableLiveData<Resource<MinistryLicenseEntity>>()
    fun uploadMinistryLicense(token:String, license: MultipartBody.Part?) {
        ministryLicense.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileUseCase.build(token, license).collect {
                    ministryLicense.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                ministryLicense.postValue(Resource.error(data = null, message = handleError(exception)))
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
                    "هذا العضو غير موجود فى قاعدة البيانات"
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