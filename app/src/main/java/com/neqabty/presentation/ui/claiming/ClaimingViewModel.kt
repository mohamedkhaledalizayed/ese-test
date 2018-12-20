package com.neqabty.presentation.ui.claiming

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllAreas
import com.neqabty.domain.usecases.GetAllDoctors
import com.neqabty.domain.usecases.GetAllSpecializations
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.AreaEntityUIMapper
import com.neqabty.presentation.mappers.DoctorEntityUIMapper
import com.neqabty.presentation.mappers.SpecializationEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class ClaimingViewModel @Inject constructor(private val getAllDoctors: GetAllDoctors, val getAllAreas: GetAllAreas, val getAllSpecializations: GetAllSpecializations) : BaseViewModel() {

    private val specializationEntityUIMapper = SpecializationEntityUIMapper()
    private val areaEntityUIMapper = AreaEntityUIMapper()
    private val doctorEntityUIMapper = DoctorEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ClaimingViewState> = MutableLiveData()

    init {
        viewState.value = ClaimingViewState()
    }

    fun getAllContent() {
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
                            onContentReceived()
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
                            onContentReceived()
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
                            onContentReceived()
                        },
                        { errorState.value = it }
                )


        addDisposable(doctorsDisposable)
        addDisposable(areasDisposable)
        addDisposable(specializationsDisposable)
    }

    private fun onContentReceived() {
        if (viewState.value?.doctors != null && viewState.value?.areas != null && viewState.value?.specializations != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }

//    private fun onDoctorsReceived(doctors: List<DoctorUI>) {
//
//        val newViewState = viewState.value?.copy(
//                doctors = doctors)
//
//        viewState.value = newViewState
//    }
//
//    private fun onAreasReceived(areas: List<AreaUI>) {
//
//        val newViewState = viewState.value?.copy(
//                areas = areas)
//
//        viewState.value = newViewState
//    }
//
//    private fun onSpecializationsReceived(specializations: List<SpecializationUI>) {
//
//        val newViewState = viewState.value?.copy(
//                specializations = specializations)
//
//        viewState.value = newViewState
//    }
}
