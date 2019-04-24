package com.neqabty.presentation.ui.medicalProviderDetails

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.AddFavorite
import com.neqabty.domain.usecases.CheckFavorite
import com.neqabty.domain.usecases.GetProviderDetails
import com.neqabty.domain.usecases.RemoveFavorite
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.mappers.ProviderEntityUIMapper
import com.neqabty.presentation.mappers.ProviderUIEntityMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject
@OpenForTesting
class MedicalProviderDetailsViewModel @Inject constructor(
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite,
    private val checkFavorite: CheckFavorite,
    private val getProviderDetails: GetProviderDetails
) : BaseViewModel() {

    private val providerEntityUIMapper = ProviderEntityUIMapper()
    private val providerUIEntityMapper = ProviderUIEntityMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalProviderDetailsViewState> = MutableLiveData()

    init {
        viewState.value = MedicalProviderDetailsViewState()
    }

    fun getProviderDetails(id: String, type: String) {
        addDisposable(getProviderDetails.getProviderDetails(id, type)
                .flatMap {
                    it.let {
                        providerEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onProviderDetailsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    fun addFavorite(providerUI: ProviderUI) {
        addDisposable(addFavorite.addFavorite(providerUIEntityMapper.mapFrom(providerUI))
                .flatMap {
                    it.let {
                        providerEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onAddFavoriteReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    fun removeFavorite(providerUI: ProviderUI) {
        addDisposable(removeFavorite.removeFavorite(providerUIEntityMapper.mapFrom(providerUI))
                .flatMap {
                    it.let {
                        providerEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onRemoveFavoriteReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    fun isFavorite(providerUI: ProviderUI) {
        addDisposable(checkFavorite.CheckFavorite(providerUIEntityMapper.mapFrom(providerUI))
                .subscribe(
                        { onCheckFavoriteReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    private fun onProviderDetailsReceived(provider: ProviderUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                providerDetails = provider)
        viewState.value = newViewState
    }

    private fun onAddFavoriteReceived(provider: ProviderUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                isFavorite = true)
        viewState.value = newViewState
    }
    private fun onRemoveFavoriteReceived(provider: ProviderUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                isFavorite = false)
        viewState.value = newViewState
    }
    private fun onCheckFavoriteReceived(isExist: Boolean) {
        val newViewState = viewState.value?.copy(isFavorite = isExist)
        viewState.value = newViewState
    }
}
