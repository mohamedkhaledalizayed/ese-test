package com.neqabty.presentation.ui.mobile

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetUserLoggedIn
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.DoctorEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper
import retrofit2.HttpException

import javax.inject.Inject
import retrofit2.adapter.rxjava2.Result.response
import android.R.string
import org.json.JSONObject

class MobileViewModel @Inject constructor(val getUserLoggedIn: GetUserLoggedIn) : BaseViewModel() {

    private val doctorEntityUIMapper = DoctorEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MobileViewState> = MutableLiveData()

    init {
        viewState.value = MobileViewState()
    }

    fun registerUser(
        mobile: String,
        mainSyndicateId: Int,
        subSyndicateId: Int,
        token: String,
        prefs: PreferencesHelper,
        userNumber: String
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(getUserLoggedIn.getUserRegistered(mobile, mainSyndicateId, subSyndicateId, token, userNumber)
                .subscribe(
                        {
                            prefs.token = token
                            prefs.mobile = mobile
                            prefs.name = it.name!!
                            prefs.isRegistered = true
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = true)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = false)
                            val exception = it as HttpException
                            if(exception.code() == 401)
//                            val jObjError = JSONObject(exception.response().errorBody()?.string())
                                errorState.value = Throwable("لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى")
                            else
                                errorState.value = it
                        }
                ))
    }
}
