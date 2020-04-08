package com.neqabty.presentation.ui.inquiryDetails

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.EncryptData
import com.neqabty.domain.usecases.PaymentInquiry
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.EncryptionUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.mappers.EncryptionEntityUIMapper
import com.neqabty.presentation.mappers.MemberEntityUIMapper
import javax.inject.Inject

class InquiryDetailsViewModel @Inject constructor(private val encryptData: EncryptData, private val paymentInquiry: PaymentInquiry) : BaseViewModel() {

    private val memberEntityUIMapper = MemberEntityUIMapper()
    private val encryptionEntityUIMapper = EncryptionEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<InquiryDetailsViewState> = MutableLiveData()

    init {
        viewState.value = InquiryDetailsViewState()
    }


    fun encryptData(username: String, password: String, description: String) {
        viewState.value?.encryptionData?.let {
            onEncryptionDataReceived(it)
        } ?: encryptData.encryptData(username, password, description)
                .map {
                    it.let {
                        encryptionEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            onEncryptionDataReceived(it)
                        },
                        { errorState.value = it }
                )
    }

    fun paymentInquiry(number: String, serviceID: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(number, serviceID)
                .map {
                    it.let {
                        memberEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onInquiryReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    private fun onEncryptionDataReceived(data: EncryptionUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                encryptionData = data)
        viewState.value = newViewState
    }

    private fun onInquiryReceived(member: MemberUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                member = member)
        viewState.value = newViewState
    }
}
