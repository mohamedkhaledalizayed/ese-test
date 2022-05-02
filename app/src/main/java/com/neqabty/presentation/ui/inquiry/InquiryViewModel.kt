package com.neqabty.presentation.ui.inquiry

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllServiceTypes
import com.neqabty.domain.usecases.GetAllServices
import com.neqabty.domain.usecases.PaymentInquiry
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.entities.ServiceTypeUI
import com.neqabty.presentation.entities.ServiceUI
import com.neqabty.presentation.mappers.MedicalRenewalPaymentEntityUIMapper
import com.neqabty.presentation.mappers.MemberEntityUIMapper
import com.neqabty.presentation.mappers.ServiceEntityUIMapper
import com.neqabty.presentation.mappers.ServiceTypeEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
        private val getAllServiceTypes: GetAllServiceTypes,
        private val getAllServices: GetAllServices,
        private val paymentInquiry: PaymentInquiry
) : BaseViewModel() {

    private val medicalRenewalPaymentEntityUIMapper = MedicalRenewalPaymentEntityUIMapper()
    private val serviceEntityUIMapper = ServiceEntityUIMapper()
    private val serviceTypeEntityUIMapper = ServiceTypeEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<InquiryViewState> = MutableLiveData()

    init {
        viewState.value = InquiryViewState()
    }

    fun getAllServiceTypes() {
        getAllServiceTypes.observable()
                .flatMap {
                    it.let {
                        serviceTypeEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            onServiceTypesReceived(it)
                        },
                        { errorState.value = handleError(it) }
                )
    }

    fun getAllServices(typeID :Int) {
        getAllServices.getAllServices(typeID)
                .flatMap {
                    it.let {
                        serviceEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
//                            onServicesReceived(it.)
                        },
                        { errorState.value = handleError(it) }
                )
    }

    fun paymentInquiry(mobileNumber: String, number: String, serviceID: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(true, mobileNumber, number, serviceID, "", "", -1, "", "")
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

    private fun onServiceTypesReceived(serviceTypes: ServiceTypeUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                serviceTypes = serviceTypes.typesList,
                services = serviceTypes.servicesList)
        viewState.value = newViewState
    }

    private fun onServicesReceived(services: List<ServiceTypeUI.Service>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                services = services)
        viewState.value = newViewState
    }

    private fun onInquiryReceived(medicalRenewalPayment: MedicalRenewalPaymentUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                medicalRenewalPayment = medicalRenewalPayment)
        viewState.value = newViewState
    }
}
