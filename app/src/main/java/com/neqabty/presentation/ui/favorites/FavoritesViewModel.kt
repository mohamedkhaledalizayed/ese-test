package com.neqabty.presentation.ui.favorites

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetFavorites
import com.neqabty.domain.usecases.RemoveFavorite
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.mappers.ProviderEntityUIMapper
import com.neqabty.presentation.mappers.ProviderUIEntityMapper

import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
    private val removeFavorite: RemoveFavorite
) : BaseViewModel() {

    private val providerEntityUIMapper = ProviderEntityUIMapper()
    private val providerUIEntityMapper = ProviderUIEntityMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<FavoritesViewState> = MutableLiveData()

    init {
        viewState.value = FavoritesViewState()
    }

    fun getFavorites() {
        addDisposable(getFavorites.observable()
                .flatMap {
                    it.let {
                        providerEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onFavoritesReceived(it) },
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
    private fun onRemoveFavoriteReceived(provider: ProviderUI) {
        getFavorites()
    }
    private fun onFavoritesReceived(favorites: List<ProviderUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                favorites = favorites)
        viewState.value = newViewState
    }
}
