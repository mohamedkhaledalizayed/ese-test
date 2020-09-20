package com.neqabty.presentation.ui.medicalProviders

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllSpecializations
import com.neqabty.domain.usecases.GetProvidersByType
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.entities.SpecializationUI
import com.neqabty.presentation.mappers.ProviderEntityUIMapper
import com.neqabty.presentation.mappers.SpecializationEntityUIMapper

import javax.inject.Inject

class MedicalProvidersViewModel @Inject constructor(private val getAllSpecializations: GetAllSpecializations, private val getProvidersByType: GetProvidersByType) : BaseViewModel() {

    private val specializationEntityUIMapper = SpecializationEntityUIMapper()
    private val providerEntityUIMapper = ProviderEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalProvidersViewState> = MutableLiveData()

    init {
        viewState.value = MedicalProvidersViewState()
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
                            errorState.value = it
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

    fun getMedicalProviders(id: String, govID: String, areaID: String, professionID: String?, degreeID: String?) {
        viewState.value?.providers?.let {
            onProvidersReceived(it)
        } ?: addDisposable(getProvidersByType.getProvidersByType(id, govID, areaID, professionID, degreeID)
                .flatMap {
                    it.let {
                        providerEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onProvidersReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    private fun onProvidersReceived(providers: List<ProviderUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                providers = providers)
        viewState.value = newViewState
    }
}
