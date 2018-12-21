package com.neqabty.presentation.ui.claiming

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.*
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class ClaimingViewModel @Inject constructor(val getAllDoctors: GetAllDoctors, val getAllAreas: GetAllAreas, val getAllSpecializations: GetAllSpecializations, val getAllDegrees: GetAllDegrees, val getAllProviders: GetAllProviders) : BaseViewModel() {

    private val degreeEntityUIMapper = DegreeEntityUIMapper()
    private val specializationEntityUIMapper = SpecializationEntityUIMapper()
    private val areaEntityUIMapper = AreaEntityUIMapper()
    private val doctorEntityUIMapper = DoctorEntityUIMapper()
    private val providerEntityUIMapper = ProviderEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ClaimingViewState> = MutableLiveData()

    init {
        viewState.value = ClaimingViewState()
    }

    fun getAllContent1() {
        val doctorsDisposable = getAllDoctors.observable()
                .flatMap {
                    it.let {
                        doctorEntityUIMapper.observable(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
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
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(areas = it)
                            onContent1Received()
                        },
                        { errorState.value = it }
                )


        val degreesdDisposable = getAllDegrees.observable()
                .flatMap {
                    it.let {
                        degreeEntityUIMapper.observable(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
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
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(specializations = it)
                            onContent1Received()
                        },
                        { errorState.value = it }
                )


        addDisposable(doctorsDisposable)
        addDisposable(areasDisposable)
        addDisposable(degreesdDisposable)
        addDisposable(specializationsDisposable)
    }

    fun getAllContent2(type:String) {
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

        val providersDisposable = getAllProviders.getAllProviders(type)
                .flatMap {
                    it.let {
                        providerEntityUIMapper.observable(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(providers = it)
                            onContent2Received()
                        },
                        { errorState.value = it }
                )


        addDisposable(areasDisposable)
        addDisposable(providersDisposable)
    }

    private fun onContent1Received() {
        if (viewState.value?.doctors != null && viewState.value?.areas != null && viewState.value?.specializations != null && viewState.value?.degrees != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }

    private fun onContent2Received() {
        if (viewState.value?.providers != null && viewState.value?.areas != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
