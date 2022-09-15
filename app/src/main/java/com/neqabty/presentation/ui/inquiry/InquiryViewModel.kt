package com.neqabty.presentation.ui.inquiry

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllServiceTypes
import com.neqabty.domain.usecases.GetAllServices
import com.neqabty.domain.usecases.PaymentInquiry
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.RenewalPaymentUI
import com.neqabty.presentation.entities.ServiceTypeUI
import com.neqabty.presentation.entities.ServiceUI
import com.neqabty.presentation.mappers.RenewalPaymentEntityUIMapper
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

    private val renewalPaymentEntityUIMapper = RenewalPaymentEntityUIMapper()
    private val serviceEntityUIMapper = ServiceEntityUIMapper()
    private val serviceTypeEntityUIMapper = ServiceTypeEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<InquiryViewState> = MutableLiveData()

    init {
        viewState.value = InquiryViewState()
    }

    fun getAllServiceTypes() {
        viewState.value?.serviceTypes?.let {
            onServiceTypesReceived(it)
        } ?: getAllServiceTypes.observable()
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
                            onServicesReceived(it)
                        },
                        { errorState.value = handleError(it) }
                )
    }

    fun paymentInquiry(number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(number)
                .map {
                    it.let {
                        renewalPaymentEntityUIMapper.mapFrom(it)
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

    private fun onServiceTypesReceived(serviceTypes: List<ServiceTypeUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                serviceTypes = serviceTypes)
        viewState.value = newViewState
    }

    private fun onServicesReceived(services: List<ServiceUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                services = services)
        viewState.value = newViewState
    }

    private fun onInquiryReceived(renewalPayment: RenewalPaymentUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
            renewalPayment = renewalPayment)
        viewState.value = newViewState
    }
}
