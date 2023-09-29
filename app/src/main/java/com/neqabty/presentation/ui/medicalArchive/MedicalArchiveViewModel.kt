package com.neqabty.presentation.ui.medicalArchive

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetArchiveUploadCategories
import com.neqabty.domain.usecases.GetArchiveUploadsList
import com.neqabty.domain.usecases.GetLiteFollowersListData
import com.neqabty.domain.usecases.ValidateUserForClaiming
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.ArchiveUploadCategoryUI
import com.neqabty.presentation.entities.ArchiveUploadItemUI
import com.neqabty.presentation.entities.ClaimingValidationUI
import com.neqabty.presentation.mappers.ArchiveUploadCategoryEntityUIMapper
import com.neqabty.presentation.mappers.ArchiveUploadItemEntityUIMapper
import com.neqabty.presentation.mappers.ClaimingValidationEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicalArchiveViewModel @Inject constructor(
    private val getArchiveUploadCategories: GetArchiveUploadCategories,
    private val getArchiveUploadsList: GetArchiveUploadsList
) : BaseViewModel() {

    private val archiveUploadCategoryEntityUIMapper = ArchiveUploadCategoryEntityUIMapper()
    private val archiveUploadItemEntityUIMapper = ArchiveUploadItemEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalArchiveViewState> = MutableLiveData()

    init {
        viewState.value = MedicalArchiveViewState(isLoading = true)
    }

    fun getCategories() {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(getArchiveUploadCategories.observable()
            .flatMap {
                it.let {
                    archiveUploadCategoryEntityUIMapper.observable(it)
                }
            }.subscribe(
                { onCategoriesReceived(it) },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )
        )
    }

    private fun onCategoriesReceived(categoriesList: List<ArchiveUploadCategoryUI>) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            archiveUploadCategoryUIList = categoriesList)
        viewState.value = newViewState
    }

    fun getUploadsList(userNumber: String, catId: Int) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(getArchiveUploadsList.getArchiveUploadsList(userNumber, catId)
            .flatMap {
                it.let {
                    archiveUploadItemEntityUIMapper.observable(it)
                }
            }.subscribe(
                { onUploadsListReceived(it) },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )
        )
    }

    private fun onUploadsListReceived(uploadsList: List<ArchiveUploadItemUI>) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            archiveUploadItemUIList = uploadsList)
        viewState.value = newViewState
    }
}
