package com.neqabty.healthcare.commen.splash.view


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.commen.splash.data.model.AppConfig
import com.neqabty.healthcare.commen.splash.domain.interactors.AppConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appConfigUseCase: AppConfigUseCase
) : ViewModel() {
    val appConfig = MutableLiveData<Resource<AppConfig>>()
    fun appConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            appConfig.postValue(Resource.loading(data = null))
            try {
                appConfigUseCase.build().collect {
                    appConfig.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                appConfig.postValue(Resource.error(data = null, handleError(e)))
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
                406 -> {
                    val errorObject = JSONObject(throwable.response()!!.errorBody()?.string())
                    errorObject.getString("message")
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                else -> {
                    "No Internet Connection"
                }
            }
        } else {
            "No Internet Connection"
        }
    }

}