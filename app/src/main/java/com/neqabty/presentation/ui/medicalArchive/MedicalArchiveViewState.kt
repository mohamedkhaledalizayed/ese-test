package com.neqabty.presentation.ui.medicalArchive

import com.neqabty.presentation.entities.*

data class MedicalArchiveViewState(
    var isLoading: Boolean = true,
    var archiveUploadCategoryUIList: List<ArchiveUploadCategoryUI>? = null,
    var archiveUploadItemUIList: List<ArchiveUploadItemUI>? = null
)
