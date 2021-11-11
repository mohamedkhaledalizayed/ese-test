package com.neqabty.presentation.ui.onlinePharmacy

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetOnlinePharmacyURL
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.OnlinePharmacyURLEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnlinePharmacyViewModel @Inject constructor(
        private val getOnlinePharmacyURL: GetOnlinePharmacyURL) : BaseViewModel() {

    private val onlinePharmacyURLEntityUIMapper = OnlinePharmacyURLEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<OnlinePharmacyViewState> = MutableLiveData()

    init {
        viewState.value = OnlinePharmacyViewState()
    }

    fun getURL(userNumber: String) {
        viewState.value?.url?.let {
            onURLReceived(it)
        } ?: addDisposable(getOnlinePharmacyURL.getURL(userNumber)
                .map {
                    it.let {
//                        it.url = "https://www.vezeeta.com/pharmacy/ar-eg?utm_campaign=EngineersyndicateMedical"
                        onlinePharmacyURLEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onURLReceived(it.url) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onURLReceived(url: String) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            url = url
        )
        viewState.value = newViewState
    }
}
