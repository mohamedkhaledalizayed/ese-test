package com.neqabty.presentation.entities

data class ArchiveUploadAcknowledgementUI(
    var id: Int = 0,
    var uploadTypeId: Int = 0,
    var userId: Int = 0,
    var name: String?,
    var description: String?,
    var doc1: String?
)