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
    private val getAllServiceTypes: GetAllServiceTypes,
    private val getAllServices: GetAllServices,
    private val paymentInquiry: PaymentInquiry
) : BaseViewModel() {

    private val medicalRenewalPaymentEntityUIMapper = MedicalRenewalPaymentEntityUIMapper()
    private val serviceEntityUIMapper = ServiceEntityUIMapper()
    private val serviceTypeEntityUIMapper = ServiceTypeEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<SyndicateServicesViewState> = MutableLiveData()

    init {
        viewState.value = SyndicateServicesViewState()
    }

    fun getAllServiceTypes(userNumber: String) {
        getAllServiceTypes.getAllServiceTypes(userNumber)
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

    fun paymentSyndicateServices(mobileNumber: String, number: String, serviceID: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(true, mobileNumber, number, serviceID, "", "", -1, "", "")
            .map {
                it.let {
                    medicalRenewalPaymentEntityUIMapper.mapFrom(it)
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

    private fun onSyndicateServicesReceived(medicalRenewalPayment: MedicalRenewalPaymentUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            medicalRenewalPayment = medicalRenewalPayment)
        viewState.value = newViewState
    }
}
