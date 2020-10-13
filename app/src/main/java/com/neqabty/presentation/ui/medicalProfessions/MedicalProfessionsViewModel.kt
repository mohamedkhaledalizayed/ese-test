package com.neqabty.presentation.ui.medicalProfessions

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllSpecializations
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.SpecializationUI
import com.neqabty.presentation.mappers.SpecializationEntityUIMapper

import javax.inject.Inject

class MedicalProfessionsViewModel @Inject constructor(private val getAllSpecializations: GetAllSpecializations) : BaseViewModel() {

    private val specializationEntityUIMapper = SpecializationEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalProfessionsViewState> = MutableLiveData()

    init {
        viewState.value = MedicalProfessionsViewState()
    }

    fun getMedicalProfessions(id: String, govID: String, areaID: String) {
        viewState.value?.professions?.let {
            onProfessionsReceived(it)
        } ?: addDisposable(getAllSpecializations.observable()
                .flatMap {
                    it.let {
                        specializationEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onProfessionsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onProfessionsReceived(professions: List<SpecializationUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                professions = professions)
        viewState.value = newViewState
    }
}
