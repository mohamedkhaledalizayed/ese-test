package com.neqabty.presentation.ui.medicalArchiveAddFile

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.ArchiveUploadAcknowledgementUI
import com.neqabty.presentation.entities.ArchiveUploadCategoryUI
import com.neqabty.presentation.entities.ArchiveUploadItemUI
import com.neqabty.presentation.entities.ClaimingValidationUI
import com.neqabty.presentation.mappers.ArchiveUploadAcknowledgementEntityUIMapper
import com.neqabty.presentation.mappers.ArchiveUploadCategoryEntityUIMapper
import com.neqabty.presentation.mappers.ArchiveUploadItemEntityUIMapper
import com.neqabty.presentation.mappers.ClaimingValidationEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MedicalArchiveAddFileViewModel @Inject constructor(
    private val getArchiveUploadCategories: GetArchiveUploadCategories,
    private val uploadToArchive: UploadToArchive
) : BaseViewModel() {

    private val archiveUploadCategoryEntityUIMapper = ArchiveUploadCategoryEntityUIMapper()
    private val archiveUploadAcknowledgementEntityUIMapper = ArchiveUploadAcknowledgementEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalArchiveAddFileViewState> = MutableLiveData()

    init {
        viewState.value = MedicalArchiveAddFileViewState(isLoading = true)
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

    fun uploadFile(userNumber: String,
                   name: String,
                   description: String,
                   catId: String,
                   docsNumber: Int,
                   doc1: File?) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(uploadToArchive.uploadToArchive(userNumber, name, description, catId, docsNumber, doc1)
            .flatMap {
                it.let {
                    archiveUploadAcknowledgementEntityUIMapper.observable(it)
                }
            }.subscribe(
                { onUploadFileAcknowledgementReceived(it) },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )
        )
    }

    private fun onUploadFileAcknowledgementReceived(acknowledgement: ArchiveUploadAcknowledgementUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            archiveUploadAcknowledgementUI = acknowledgement)
        viewState.value = newViewState
    }
}
