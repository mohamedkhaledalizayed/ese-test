package com.neqabty.presentation.ui.medicalRenew

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalRenewalData
import com.neqabty.domain.usecases.MedicalRenewPaymentInquiry
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.mappers.MedicalRenewalEntityUIMapper
import com.neqabty.presentation.mappers.MedicalRenewalPaymentEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicalRenewViewModel @Inject constructor(
        private val getMedicalRenewalData: GetMedicalRenewalData,
        private val paymentInquiry: MedicalRenewPaymentInquiry
) : BaseViewModel() {

    private val medicalRenewalPaymentEntityUIMapper = MedicalRenewalPaymentEntityUIMapper()
    private val medicalRenewalEntityUIMapper = MedicalRenewalEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalRenewViewState> = MutableLiveData()

    init {
        viewState.value = MedicalRenewViewState()
    }


    fun getMedicalRenewalData(mobileNumber: String, number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)

//        viewState.value?.medicalRenewalUI?.let {
//            onMedicalRenewalDataReceived(it)
//        } ?:
        addDisposable(getMedicalRenewalData.getMedicalRenewalData(mobileNumber, number)
                    .map {
                        it.let {
                            medicalRenewalEntityUIMapper.mapFrom(it)
                        }
                    }.subscribe(
                            { onMedicalRenewalDataReceived(it) },
                            {
                                viewState.value = viewState.value?.copy(isLoading = false)
                                errorState.value = handleError(it)
                            }
                    )
            )
    }


    private fun onMedicalRenewalDataReceived(medicalRenewalData: MedicalRenewalUI) {
        val newViewState = viewState.value?.copy(
                isLoading = true,
                medicalRenewalUI = medicalRenewalData)
        viewState.value = newViewState
    }

    fun paymentInquiry(mobileNumber: String, number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(true, mobileNumber, number, "", 1, "", -1, -1, "", "")
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

    private fun onInquiryReceived(medicalRenewalPayment: MedicalRenewalPaymentUI) {
        val newViewState = viewState.value?.copy(
                isLoading = true,
                medicalRenewalPayment = medicalRenewalPayment)
        viewState.value = newViewState
    }
}
