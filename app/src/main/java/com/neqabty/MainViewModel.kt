package com.neqabty

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAds
import com.neqabty.domain.usecases.GetAppConfig
import com.neqabty.domain.usecases.Login
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.AdUI
import com.neqabty.presentation.entities.AppConfigUI
import com.neqabty.presentation.entities.UserUI
import com.neqabty.presentation.mappers.AdEntityUIMapper
import com.neqabty.presentation.mappers.AppConfigEntityUIMapper
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
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
                            Constants.isEditFollowersEnabled = (it.editFollowersStatus.status.toInt() == 1)
                            Constants.editFollowersStatusMsg = it.editFollowersStatus.statusMsg
                            Constants.CC_COMMISSION = it.cardCommission / 100
                            Constants.POS_COMMISSION = it.posCommission / 100
                            Constants.FAWRY_COMMISSION = it.fawryCommission / 100
                            Constants.MIN_COMMISSION = it.minCommission
                            Constants.hasQuestionnaire.postValue(it.hasQuestionnaire)
                            Constants.isSyndicatesListEnabled.postValue(it.isSyndicatesListEnabled)
                            Constants.YODAWY_CONFIG.postValue(AppConfigUI.YodawyStatus(it.yodawyConfig.status, it.yodawyConfig.url, it.yodawyConfig.publicKey, it.yodawyConfig.totalAmount, it.yodawyConfig.deliverySentence))
                            Constants.VEZEETA_CONFIG.postValue(AppConfigUI.VezeetaStatus(it.vezeetaConfig.status, it.vezeetaConfig.url))
                            viewState.postValue(viewState.value?.copy(appConfigUI = appConfigEntityUIMapper.mapFrom(it)))
                            onConfigReceived()
                        },
                        {
                            getAppConfig()
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
