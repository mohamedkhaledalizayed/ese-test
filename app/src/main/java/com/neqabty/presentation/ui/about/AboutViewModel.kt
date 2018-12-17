package com.neqabty.presentation.ui.about

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetSyndicate
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.mappers.SyndicateEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class AboutViewModel @Inject constructor(private val getSyndicate: GetSyndicate) : BaseViewModel() {

    private val syndicateEntityUIMapper = SyndicateEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<AboutViewState> = MutableLiveData()

    init {
        viewState.value = AboutViewState()
    }

    fun getSyndicate(id : String) {
        addDisposable(getSyndicate.getSyndicateById(id)
                .map {
                    it.let {
                        syndicateEntityUIMapper.mapFrom(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        { onSyndicateReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }


    private fun onSyndicateReceived(syndicate: SyndicateUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                syndicate = syndicate)
        viewState.value = newViewState
    }
}
