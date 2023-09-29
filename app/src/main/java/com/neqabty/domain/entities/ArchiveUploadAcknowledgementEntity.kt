package com.neqabty.domain.entities

data class ArchiveUploadAcknowledgementEntity(
    var id: Int = 0,
    var uploadTypeId: Int = 0,
    var userId: Int = 0,
    var name: String?,
    var description: String?,
    var doc1: String?
)