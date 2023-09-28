package com.neqabty.presentation.ui.medicalBuyService

import androidx.lifecycle.MutableLiveData
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicalBuyServiceViewModel @Inject constructor(
) : BaseViewModel() {

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalBuyServiceViewState> = MutableLiveData()

    init {
        viewState.value = MedicalBuyServiceViewState(isLoading = false)
    }
}
