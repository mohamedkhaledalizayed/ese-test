package com.neqabty.presentation.ui.medicalProviders

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalDirectoryLookups
import com.neqabty.domain.usecases.GetMedicalDirectoryProviders
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalDirectoryLookupsUI
import com.neqabty.presentation.entities.MedicalDirectoryProviderUI
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.mappers.MedicalDirectoryLookupsEntityUIMapper
import com.neqabty.presentation.mappers.MedicalDirectoryProviderEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicalProvidersViewModel @Inject constructor(private val getAllSpecializations: GetMedicalDirectoryLookups, private val getProvidersByType: GetMedicalDirectoryProviders) : BaseViewModel() {

    private val medicalDirectoryLookupsEntityUIMapper = MedicalDirectoryLookupsEntityUIMapper()
    private val medicalDirectoryProviderEntityUIMapper = MedicalDirectoryProviderEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalProvidersViewState> = MutableLiveData()

    init {
        viewState.value = MedicalProvidersViewState()
    }

    fun getMedicalProfessions(mobileNumber: String) {
        viewState.value?.specializations?.let {
            onProfessionsReceived(it)
        } ?: addDisposable(getAllSpecializations.getLookups(mobileNumber)
                .map {
                    it.let {
                        medicalDirectoryLookupsEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onProfessionsReceived(it.specializations?: mutableListOf()) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onProfessionsReceived(professions: List<MedicalDirectoryLookupsUI.DoctorSpecialization>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                specializations = professions)
        viewState.value = newViewState
    }

    fun getMedicalProviders(
        mobileNumber: String,
        providerTypeId: String,
        govId: String,
        areaId: String,
        providerName: String,
        specializationId: String
    ) {
        viewState.value?.providers?.let {
            onProvidersReceived(it)
        } ?: addDisposable(getProvidersByType.getProviders(mobileNumber, providerTypeId, govId, if(areaId.equals("-1")) "" else areaId, providerName, specializationId)
                .flatMap {
                    it.let {
                        medicalDirectoryProviderEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onProvidersReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onProvidersReceived(providers: List<MedicalDirectoryProviderUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                providers = providers)
        viewState.value = newViewState
    }

    fun mapProviders(providerUI: MedicalDirectoryProviderUI): ProviderUI {
        return ProviderUI(
            id = providerUI.id.toInt(),
            name = providerUI.name,
            address = providerUI.address,
            phones = providerUI.phone,
            emails = providerUI.email,
            typeID = providerUI.typeId,
            typeName = providerUI.typeName,
            governId = "",
            areaId = "",
            createdAt = "",
            createdBy = "",
            updatedAt = "",
            updatedBy = "")
    }
}
