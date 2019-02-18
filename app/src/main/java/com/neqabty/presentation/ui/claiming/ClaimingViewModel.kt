package com.neqabty.presentation.ui.claiming

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.mappers.*
import com.neqabty.testing.OpenForTesting
import java.io.File
import javax.inject.Inject

@OpenForTesting
class ClaimingViewModel @Inject constructor(val getAllDoctors: GetAllDoctors, val getAllAreas: GetAllAreas, val getAllSpecializations: GetAllSpecializations, val getAllDegrees: GetAllDegrees, val getAllProviders: GetAllProviders, val getAllProvidersTypes: GetAllProvidersTypes, val sendMedicalRequest: SendMedicalRequest, private val validateUser: ValidateUser) : BaseViewModel() {

    private val degreeEntityUIMapper = DegreeEntityUIMapper()
    private val specializationEntityUIMapper = SpecializationEntityUIMapper()
    private val areaEntityUIMapper = AreaEntityUIMapper()
    private val doctorEntityUIMapper = DoctorEntityUIMapper()
    private val providerTypeEntityUIMapper = ProviderTypeEntityUIMapper()
    private val providerEntityUIMapper = ProviderEntityUIMapper()
    private val memberEntityUIMapper = MemberEntityUIMapper()


    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ClaimingViewState> = MutableLiveData()

    init {
        viewState.value = ClaimingViewState(isLoading = true)
    }

    fun getAllContent1() {
        val doctorsDisposable = getAllDoctors.observable()
                .flatMap {
                    it.let {
                        doctorEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(doctors = it)
                            onContent1Received()
                        },
                        { errorState.value = it }
                )


        val areasDisposable = getAllAreas.observable()
                .flatMap {
                    it.let {
                        areaEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(areas = it)
                            onContent1Received()
                        },
                        { errorState.value = it }
                )


        val degreesDisposable = getAllDegrees.observable()
                .flatMap {
                    it.let {
                        degreeEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(degrees = it)
                            onContent1Received()
                        },
                        { errorState.value = it }
                )


        val specializationsDisposable = getAllSpecializations.observable()
                .flatMap {
                    it.let {
                        specializationEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(specializations = it)
                            onContent1Received()
                        },
                        { errorState.value = it }
                )

        viewState.value?.doctors?.let {
            onContent1Received()
        } ?: addDisposable(doctorsDisposable)

        viewState.value?.areas?.let {
            onContent1Received()
        } ?: addDisposable(areasDisposable)

        viewState.value?.degrees?.let {
            onContent1Received()
        } ?: addDisposable(degreesDisposable)

        viewState.value?.specializations?.let {
            onContent1Received()
        } ?: addDisposable(specializationsDisposable)
    }

    fun getAllContent2(type: String) {
        val areasDisposable = getAllAreas.observable()
                .flatMap {
                    it.let {
                        areaEntityUIMapper.observable(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(areas = it)
                            onContent2Received()
                        },
                        { errorState.value = it }
                )

        val providersTypesDisposable = getAllProvidersTypes.observable()
                .flatMap {
                    it.let {
                        providerTypeEntityUIMapper.observable(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(providerTypes = it)
                            onContent2Received()
                        },
                        { errorState.value = it }
                )

        viewState.value?.areas?.let {
            onContent2Received()
        } ?: addDisposable(areasDisposable)

        viewState.value?.providerTypes?.let {
            onContent2Received()
        } ?: addDisposable(providersTypesDisposable)

        getProvidersByType(type)
    }

    fun getProvidersByType(type: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        val providersDisposable = getAllProviders.getAllProviders(type)
                .flatMap {
                    it.let {
                        providerEntityUIMapper.observable(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, providers = it)
                            onContent2Received()
                        },
                        { errorState.value = it }
                )
        viewState.value?.providers?.let {
            onContent2Received()
        } ?: addDisposable(providersDisposable)
    }


    fun sendMedicalRequest(mainSyndicateId: Int, subSyndicateId: Int, userNumber: String, email: String, phone: String, profession: Int, degree: Int, area: Int, doctor: Int, providerType: Int, provider: Int, docsNumber: Int, doc1: File?, doc2: File?, doc3: File?, doc4: File?, doc5: File?) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(sendMedicalRequest.sendMedicalRequest(mainSyndicateId, subSyndicateId, userNumber, email, phone, profession, degree, area, doctor, providerType, provider, docsNumber, doc1, doc2, doc3, doc4, doc5)
                .subscribe(
                        { viewState.value = viewState.value?.copy(isLoading = false) },
                        { errorState.value = it }
                )
        )
    }


    fun validateUser(number : String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(validateUser.validateUser(number)
                .map {
                    it.let {
                        memberEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onValidationReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
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
    private fun onContent1Received() {
        if (viewState.value?.doctors != null && viewState.value?.areas != null && viewState.value?.specializations != null && viewState.value?.degrees != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }

    private fun onContent2Received() {
        if (viewState.value?.providers != null && viewState.value?.areas != null && viewState.value?.providerTypes != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
