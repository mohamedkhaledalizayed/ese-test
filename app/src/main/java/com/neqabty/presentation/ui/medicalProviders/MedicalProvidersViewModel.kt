package com.neqabty.presentation.ui.medicalProviders

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetProvidersByType
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.mappers.ProviderEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject
@OpenForTesting
class MedicalProvidersViewModel @Inject constructor(private  val getProvidersByType: GetProvidersByType) : BaseViewModel() {

    private val providerEntityUIMapper = ProviderEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalProvidersViewState> = MutableLiveData()

    init {
        viewState.value = MedicalProvidersViewState()
    }

    fun getMedicalProviders(id:String,govID:String,areaID:String,professionID:String?,degreeID:String?) {
        viewState.value?.providers?.let {
            onProvidersReceived(it)
        } ?: addDisposable(getProvidersByType.getProvidersByType(id,govID,areaID,professionID, degreeID)
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
