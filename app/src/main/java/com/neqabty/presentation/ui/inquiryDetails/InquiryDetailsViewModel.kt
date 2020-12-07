package com.neqabty.presentation.ui.inquiryDetails

import androidx.lifecycle.MutableLiveData
import com.neqabty.data.api.WebService
import com.neqabty.data.api.requests.SyndicateRequest
import com.neqabty.domain.usecases.EncryptData
import com.neqabty.domain.usecases.PaymentInquiry
import com.neqabty.domain.usecases.SendDecryptionKey
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.DecryptionUI
import com.neqabty.presentation.entities.EncryptionUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.mappers.DecryptionEntityUIMapper
import com.neqabty.presentation.mappers.EncryptionEntityUIMapper
import com.neqabty.presentation.mappers.MemberEntityUIMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InquiryDetailsViewModel @Inject constructor(
    private val sendDecryptionKey: SendDecryptionKey,
    private val encryptData: EncryptData,
    private val paymentInquiry: PaymentInquiry,
    private val api: WebService
) : BaseViewModel() {

    private val memberEntityUIMapper = MemberEntityUIMapper()
    private val encryptionEntityUIMapper = EncryptionEntityUIMapper()
    private val decryptionEntityUIMapper = DecryptionEntityUIMapper()

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
                        { errorState.value = handleError(it) }
                )
    }

    fun sendDecryptionKey(requestNumber: String, decryptionKey: String) {
        viewState.value?.decryptionData?.let {
            onSendDecryptionDataReceived(it)
        } ?: sendDecryptionKey.sendDecryptionKey(requestNumber, decryptionKey)
                .map {
                    it.let {
                        decryptionEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            onSendDecryptionDataReceived(it)
                        },
                        { errorState.value = handleError(it) }
                )
    }

    fun paymentInquiry(number: String, serviceID: String, requestID: String, amount: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(number, serviceID, requestID, amount)
                .map {
                    it.let {
                        memberEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onInquiryReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
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

    private fun onSendDecryptionDataReceived(data: DecryptionUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                decryptionData = data)
        viewState.value = newViewState
    }

    private fun onInquiryReceived(member: MemberUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                member = member)
        viewState.value = newViewState
    }
//
//    fun setPaid(username: String) {
//        api.setPaid(SyndicateRequest(username)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
//                {
//                    viewState.value = viewState.value?.copy(isLoading = false)
//                },
//                { errorState.value = handleError(it) }
//        )
//    }

}
