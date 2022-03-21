package com.neqabty.superneqabty.splash.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.superneqabty.core.utils.AppUtils
import com.neqabty.superneqabty.core.utils.Resource
import com.neqabty.superneqabty.splash.data.model.AppConfig
import com.neqabty.superneqabty.splash.domain.interactors.AppConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
                appConfig.postValue(Resource.error(data = null, AppUtils().handleError(e)))
                Log.e("", e.toString())
            }
        }
    }
}