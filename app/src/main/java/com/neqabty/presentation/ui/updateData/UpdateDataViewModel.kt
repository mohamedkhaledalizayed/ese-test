package com.neqabty.presentation.ui.updateData

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetUpdateUserDataInquiry
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.InquireUpdateUserDataUI
import com.neqabty.presentation.mappers.InquireUpdateUserDataEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateDataViewModel @Inject constructor(private val getUpdateUserDataInquiry: GetUpdateUserDataInquiry) : BaseViewModel() {

    private val updateUserDataEntityUIMapper = InquireUpdateUserDataEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<UpdateDataViewState> = MutableLiveData()

    init {
        viewState.value = UpdateDataViewState()
    }

    fun inquireUpdateUserData(number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(getUpdateUserDataInquiry.inquireUpdateUserData(number)
                .map {
                    it.let {
                        updateUserDataEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onUpdateUserDataReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onUpdateUserDataReceived(userDataInquire: InquireUpdateUserDataUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                userDataInquire = userDataInquire)
        viewState.value = newViewState
    }
}
