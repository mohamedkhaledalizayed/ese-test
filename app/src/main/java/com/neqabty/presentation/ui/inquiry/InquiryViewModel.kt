package com.neqabty.presentation.ui.inquiry

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.ValidateUser
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.mappers.MemberEntityUIMapper

import javax.inject.Inject

class InquiryViewModel @Inject constructor(private val validateUser: ValidateUser) : BaseViewModel() {

    private val memberEntityUIMapper = MemberEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<InquiryViewState> = MutableLiveData()

    init {
        viewState.value = InquiryViewState()
    }

    fun validateUser(number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(validateUser.validateUser(number)
                .map {
                    it.let {
                        memberEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onValidationReceived(it) },
                        {
                            onValidationReceived(MemberUI("3308222","MonaZ","2-2-2020","MasterCard","1-1-2020",200,"AR","2-2-2020","MSG",119))
//                            viewState.value = viewState.value?.copy(isLoading = false)
//                            errorState.value = it
                        }
                )
        )
    }

    private fun onValidationReceived(member: MemberUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                member = member)
        viewState.value = newViewState
    }
}
