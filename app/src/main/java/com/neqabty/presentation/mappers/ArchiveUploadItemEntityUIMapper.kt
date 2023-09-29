package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ArchiveUploadItemEntity
import com.neqabty.presentation.entities.ArchiveUploadItemUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArchiveUploadItemEntityUIMapper @Inject constructor() : Mapper<ArchiveUploadItemEntity, ArchiveUploadItemUI>() {

    override fun mapFrom(from: ArchiveUploadItemEntity): ArchiveUploadItemUI {
        return ArchiveUploadItemUI(
            id = from.id,
            name = from.name,
            description = from.description,
            categoryId = from.categoryId,
            doc1 = from.doc1
        )
    }
}
