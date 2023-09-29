package com.neqabty.presentation.ui.medicalArchiveAddFile

import com.neqabty.presentation.entities.*

data class MedicalArchiveAddFileViewState(
    var isLoading: Boolean = true,
    var archiveUploadCategoryUIList: List<ArchiveUploadCategoryUI>? = null,
    var archiveUploadAcknowledgementUI: ArchiveUploadAcknowledgementUI? = null
)
