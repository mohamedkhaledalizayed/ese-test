package com.neqabty.presentation.ui.ads

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAds
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.AdUI
import com.neqabty.presentation.mappers.AdEntityUIMapper

import javax.inject.Inject

class AdsViewModel @Inject constructor(private val getAds: GetAds) : BaseViewModel() {

    private val adEntityUIMapper = AdEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<AdsViewState> = MutableLiveData()

    init {
        viewState.value = AdsViewState()
    }

    fun getAds(sectionID: Int) {
        viewState.value?.ad?.let {
            onAdsReceived(it)
        } ?: addDisposable(getAds.getAds(sectionID)
                .map {
                    it.let {
                        adEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onAdsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onAdsReceived(adUI: AdUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                ad = adUI)
        viewState.value = newViewState
    }
}
