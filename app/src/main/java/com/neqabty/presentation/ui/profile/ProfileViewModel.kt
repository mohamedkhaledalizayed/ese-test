package com.neqabty.presentation.ui.profile

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetProfile
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.ProfileUI
import com.neqabty.presentation.mappers.ProfileEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val getProfile: GetProfile
) : BaseViewModel() {

    private val profileEntityUIMapper = ProfileEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ProfileViewState> = MutableLiveData()

    init {
        viewState.value = ProfileViewState(isLoading = true)
    }

    fun getProfile(mobile: String, userNumber: String) {
        viewState.value?.profile?.let {
            onProfileReceived(it)
        } ?: addDisposable(getProfile.getProfile(mobile, userNumber)
                .flatMap {
                    it.let {
                        profileEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onProfileReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onProfileReceived(profileUI: ProfileUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                profile = profileUI
        )
        viewState.value = newViewState
    }
}
