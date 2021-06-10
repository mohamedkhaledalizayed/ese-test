package com.neqabty.presentation.ui.changeUserMobile

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.ChangeUserMobile
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.ChangeUserMobileEntityUIMapper
import javax.inject.Inject

class ChangeUserMobileViewModel @Inject constructor(val changeUserMobile: ChangeUserMobile) : BaseViewModel() {

    val changeUserMobileEntityUIMapper = ChangeUserMobileEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ChangeUserMobileViewState> = MutableLiveData()

    init {
        viewState.value = ChangeUserMobileViewState()
    }


    fun changeUserMobile(userNumber: String, natID: String, newMobile: String, oldMobile: String) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(changeUserMobile.changeUserMobile(userNumber, natID, newMobile, oldMobile)
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, changeUserMobileUI = changeUserMobileEntityUIMapper.mapFrom(it))
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                ))
    }
}
