package com.neqabty.presentation.ui.ads

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAds
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.AdEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdsViewModel @Inject constructor(private val getAds: GetAds) : BaseViewModel() {

    private val adEntityUIMapper = AdEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<AdsViewState> = MutableLiveData()

    init {
        viewState.value = AdsViewState()
    }
}
