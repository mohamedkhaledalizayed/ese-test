package com.neqabty.healthcare.commen.mypackages.subscriptiondetails.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.commen.mypackages.packages.domain.entity.ProfileEntity
import com.neqabty.healthcare.commen.mypackages.packages.domain.usecases.GetMyPackagesUseCase
import com.neqabty.healthcare.commen.mypackages.subscriptiondetails.domain.usecases.DeleteFollowerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SubscriptionDetailsViewModel @Inject constructor(private val deleteFollowerUseCase: DeleteFollowerUseCase,
                                                       private val getMyPackagesUseCase: GetMyPackagesUseCase
): ViewModel() {

    val myPackages = MutableLiveData<Resource<ProfileEntity>>()
    fun getMyPackages(phone: String){
        viewModelScope.launch(Dispatchers.IO){
            myPackages.postValue(Resource.loading(data = null))

            try {
                getMyPackagesUseCase.build(phone)
                    .collect { myPackages.postValue(Resource.success(data = it)) }
            }catch (e: Throwable){
                myPackages.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    val followerStatus = MutableLiveData<Resource<Boolean>>()
    fun deleteFollower(followerId: Int, mobile: String, subscriberId: String){
        viewModelScope.launch(Dispatchers.IO){
            followerStatus.postValue(Resource.loading(data = null))

            try {
                deleteFollowerUseCase.build(followerId, mobile, subscriberId).collect {
                    followerStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                followerStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }


    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                401 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                403 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                404 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                else -> {
                    throwable.message()
                }
            }
        } else {
            throwable.message ?: ""
        }
    }
}