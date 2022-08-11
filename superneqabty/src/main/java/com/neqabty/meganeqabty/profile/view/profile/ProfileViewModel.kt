package com.neqabty.meganeqabty.profile.view.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.core.utils.Resource
import com.neqabty.meganeqabty.profile.domain.entity.profile.ProfileEntity
import com.neqabty.meganeqabty.profile.domain.interactors.ProfileUseCase
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