package com.neqabty.presentation.ui.medicalRenewDetails

import androidx.lifecycle.MutableLiveData
import com.neqabty.data.api.WebService
import com.neqabty.data.api.requests.SyndicateRequest
import com.neqabty.data.repositories.RemoteNeqabtyDataStore
import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.usecases.EncryptData
import com.neqabty.domain.usecases.MedicalRenewPaymentInquiry
import com.neqabty.domain.usecases.SendDecryptionKey
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.DecryptionUI
import com.neqabty.presentation.entities.EncryptionUI
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.mappers.DecryptionEntityUIMapper
import com.neqabty.presentation.mappers.EncryptionEntityUIMapper
import com.neqabty.presentation.mappers.MedicalRenewalPaymentEntityUIMapper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MedicalRenewDetailsViewModel @Inject constructor(
        private val sendDecryptionKey: SendDecryptionKey,
        private val encryptData: EncryptData,
        private val paymentInquiry: MedicalRenewPaymentInquiry,
        private val api: WebService
) : BaseViewModel() {

    private val medicalRenewalPaymentEntityUIMapper = MedicalRenewalPaymentEntityUIMapper()
    private val encryptionEntityUIMapper = EncryptionEntityUIMapper()
    private val decryptionEntityUIMapper = DecryptionEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalRenewDetailsViewState> = MutableLiveData()

    init {
        viewState.value = MedicalRenewDetailsViewState()
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

//    fun setPaid(username: String) {
//        api.setPaid(SyndicateRequest(username)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
//                {
//                    viewState.value = viewState.value?.copy(isLoading = false)
//                },
//                { errorState.value = handleError(it) }
//        )
//    }

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

    fun paymentInquiry(number: String, deliveryType: Int, address: String, mobile: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(number, deliveryType, address, mobile)
                .map {
                    it.let {
                        medicalRenewalPaymentEntityUIMapper.mapFrom(it)
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

    private fun onInquiryReceived(medicalRenewalPayment: MedicalRenewalPaymentUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                medicalRenewalPayment = medicalRenewalPayment)
        viewState.value = newViewState
    }
}
