package com.neqabty.presentation.ui.inquiryDetails

import androidx.lifecycle.MutableLiveData
import com.neqabty.data.api.WebService
import com.neqabty.data.api.requests.SyndicateRequest
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.di.DI
import com.neqabty.presentation.entities.*
import com.neqabty.presentation.mappers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class InquiryDetailsViewModel @Inject constructor(
    private val sendDecryptionKey: SendDecryptionKey,
    private val encryptData: EncryptData,
    private val paymentInquiry: PaymentInquiry,
    private val addRenewalRequest: AddRenewalRequest,
    private val createFawryTransaction: CreateFawryTransaction,
    @Named(DI.authorized) private val api: WebService
) : BaseViewModel() {

    private val paymentRequestEntityUIMapper = PaymentRequestEntityUIMapper()
    private val medicalRenewalPaymentEntityUIMapper = MedicalRenewalPaymentEntityUIMapper()
    private val fawryTransactionEntityUIMapper = FawryTransactionEntityUIMapper()
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

    fun paymentInquiry(mobileNumber: String, number: String, serviceID: String, requestID: String, amount: String, deliveryType: Int, address: String, mobile: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(false, mobileNumber, number, serviceID, requestID, amount, deliveryType, address, mobile)
                .map {
                    it.let {
                        medicalRenewalPaymentEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            onInquiryReceived(it)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    fun addRenewalRequest(mobileNumber: String, number: String, name: String, serviceID: Int, paymentType: String, paymentGatewayId: Int, deliveryType: Int, address: String, mobile: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(addRenewalRequest.addRenewalRequest(mobileNumber, number, name, serviceID, paymentType, paymentGatewayId, deliveryType, address, mobile)
            .map {
                it.let {
                    paymentRequestEntityUIMapper.mapFrom(it)
                }
            }.subscribe(
                {
                    onAddRequestReceived(it)
                },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )
        )
    }

    fun createFawryTransaction(refID: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(createFawryTransaction.createFawryTransaction(refID)
            .map {
                it.let {
                    fawryTransactionEntityUIMapper.mapFrom(it)
                }
            }.subscribe(
                {
                    onFawryCodeReceived(it)
                },
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

    private fun onInquiryReceived(medicalRenewalPayment: MedicalRenewalPaymentUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                medicalRenewalPayment = medicalRenewalPayment)
        viewState.value = newViewState
    }

    private fun onAddRequestReceived(paymentRequestUI: PaymentRequestUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            paymentRequestUI = paymentRequestUI)
        viewState.value = newViewState
    }

    private fun onFawryCodeReceived(fawryTransactionUI: FawryTransactionUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            fawryTransactionUI = fawryTransactionUI)
        viewState.value = newViewState
    }

    private fun onFawryCodeReceived(fawryTransactionUI: FawryTransactionUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            fawryTransactionUI = fawryTransactionUI)
        viewState.value = newViewState
    }

//    fun setPaid(username: String) {
//        api.setPaid(SyndicateRequest(username)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
//                {
//                    viewState.value = viewState.value?.copy(isLoading = false)
//                },
//                { errorState.value = handleError(it) }
//        )
//    }

}
