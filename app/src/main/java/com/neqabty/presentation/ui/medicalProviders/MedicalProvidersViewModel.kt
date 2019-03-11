package com.neqabty.presentation.ui.medicalProviders

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalProviders
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalProviderUI
import com.neqabty.presentation.mappers.MedicalProviderEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject
@OpenForTesting
class MedicalProvidersViewModel @Inject constructor(private val getMedicalProviders: GetMedicalProviders) : BaseViewModel() {

    private val medicalProviderEntityUIMapper = MedicalProviderEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalProvidersViewState> = MutableLiveData()

    init {
        viewState.value = MedicalProvidersViewState()
    }

    fun getMedicalProviders(id:String) {
        viewState.value?.providers?.let {
            onProvidersReceived(it)
        } ?: addDisposable(getMedicalProviders.getMedicalProviders(id)
                .flatMap {
                    it.let {
                        medicalProviderEntityUIMapper.observable(it)
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


    private fun onProvidersReceived(providers: List<MedicalProviderUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                providers = providers)
        viewState.value = newViewState
    }
}
