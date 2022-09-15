package com.neqabty.presentation.ui.syndicateServices

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.*
import com.neqabty.presentation.mappers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SyndicateServicesViewModel @Inject constructor(
    private val getSyndicateServices: GetSyndicateServices,
    private val getAllServices: GetAllServices,
    private val paymentInquiry: PaymentInquiry
) : BaseViewModel() {

    private val renewalPaymentEntityUIMapper = RenewalPaymentEntityUIMapper()
    private val serviceEntityUIMapper = ServiceEntityUIMapper()
    private val serviceTypeEntityUIMapper = SyndicateServicesEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<SyndicateServicesViewState> = MutableLiveData()

    init {
        viewState.value = SyndicateServicesViewState()
    }

    fun getSyndicateServices(userNumber: String) {
        getSyndicateServices.getSyndicateServices(userNumber)
            .flatMap {
                it.let {
                    serviceTypeEntityUIMapper.observable(it)
                }
            }.subscribe(
                {
                    onSyndicateServicessReceived(it)
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

    fun paymentSyndicateServices(mobileNumber: String, number: String, serviceID: Int) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(number)
            .map {
                it.let {
                    renewalPaymentEntityUIMapper.mapFrom(it)
                }
            }.subscribe(
                {
                    onSyndicateServicesReceived(it)
                },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )
        )
    }

    private fun onSyndicateServicessReceived(serviceTypes: SyndicateServicesUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            serviceTypes = serviceTypes.typesList,
            services = serviceTypes.servicesList)
        viewState.value = newViewState
    }

    private fun onServicesReceived(services: List<SyndicateServicesUI.Service>) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            services = services)
        viewState.value = newViewState
    }

    private fun onSyndicateServicesReceived(renewalPayment: RenewalPaymentUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            renewalPayment = renewalPayment)
        viewState.value = newViewState
    }
}
