package com.neqabty.domain.entities

data class ArchiveUploadItemEntity(
    var id: Int = 0,
    var name: String?,
    var description: String?,
    var categoryId: Int = 0,
    var doc1: String?
)