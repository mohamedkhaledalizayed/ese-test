package com.neqabty.presentation.ui.updateDataDetails

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetUpdateUserDataInquiry
import com.neqabty.domain.usecases.UpdateUserData
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.InquireUpdateUserDataUI
import com.neqabty.presentation.entities.UpdateUserDataUI
import com.neqabty.presentation.mappers.InquireUpdateUserDataEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateDataDetailsViewModel @Inject constructor(
    private val getUpdateUserDataInquiry: GetUpdateUserDataInquiry,
    private val updateUserData: UpdateUserData
) : BaseViewModel() {

    private val updateUserDataEntityUIMapper = InquireUpdateUserDataEntityUIMapper()
    private val UpdateUserDataEntityUIMapper = com.neqabty.presentation.mappers.UpdateUserDataEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<UpdateDataDetailsViewState> = MutableLiveData()

    init {
        viewState.value = UpdateDataDetailsViewState()
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

    fun updateUserData(
        userNumber: String,
        fullName: String,
        nationalID: String,
        mobile: String,
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(updateUserData.updateUserData(userNumber, fullName, nationalID, mobile, docsNumber, doc1, doc2, doc3)
                .map {
                    it.let {
                        UpdateUserDataEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onDataReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onDataReceived(updateUserDataUI: UpdateUserDataUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                message = updateUserDataUI.message)
        viewState.value = newViewState
    }

    private fun onUpdateUserDataReceived(userDataInquire: InquireUpdateUserDataUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                userDataInquire = userDataInquire)
        viewState.value = newViewState
    }
}
