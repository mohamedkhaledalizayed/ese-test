package com.neqabty

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAds
import com.neqabty.domain.usecases.GetAppConfig
import com.neqabty.domain.usecases.Login
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.AdUI
import com.neqabty.presentation.entities.UserUI
import com.neqabty.presentation.mappers.AdEntityUIMapper
import com.neqabty.presentation.mappers.AppConfigEntityUIMapper
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper
import javax.inject.Inject

class MainViewModel @Inject constructor(val login: Login,
                                        private val getAppConfig: GetAppConfig,
                                        private val getAds: GetAds) : BaseViewModel() {
    private val userEntityToUIMapper = UserEntityUIMapper()
    private val appConfigEntityUIMapper = AppConfigEntityUIMapper()
    private val adEntityUIMapper = AdEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewState.value = MainViewState()
    }

    fun login(
            mobile: String,
            userNumber: String,
            token: String,
            prefs: PreferencesHelper
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(login.login(Login.PARAM_ACTION_UPDATE_TOKEN, mobile, userNumber, token, prefs.token,"")
                .map {
                    it.let {
                        userEntityToUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            prefs.token = token
                            onUserReceived(it)
                        },
                        { errorState.value = handleError(it) }
                )
        )
    }

    fun getAppConfig() {
        viewState.value = viewState.value?.copy(isLoading = true)
        viewState.value?.appConfigUI?.let {
            onConfigReceived()
        } ?: addDisposable(getAppConfig.observable()
                .subscribe(
                        {
//                            it.appVersion = "145"
                            Constants.isHealthCareProjectEnabled = (it.healthCareStatus.status.toInt() == 1)
                            Constants.healthCareProjectStatusMsg = it.healthCareStatus.statusMsg
                            viewState.value = viewState.value?.copy(appConfigUI = appConfigEntityUIMapper.mapFrom(it))
                            onConfigReceived()
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }


    fun getAds(sectionID: Int) {
        addDisposable(getAds.getAds(sectionID)
                .flatMap {
                    it.let {
                        adEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onAdsReceived(it) },
                        {
                            getAds(sectionID)
                        }
                )
        )
    }

    private fun onUserReceived(user: UserUI) {

        val newViewState = viewState.value?.copy(
                isLoading = false,
                user = user)

        viewState.value = newViewState
    }


    private fun onConfigReceived() {
        if (viewState.value?.appConfigUI != null)// && viewState.value?.news != null && viewState.value?.trips != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }

    private fun onAdsReceived(adsList: List<AdUI>) {
        Constants.adsList.value = adsList
    }
}
