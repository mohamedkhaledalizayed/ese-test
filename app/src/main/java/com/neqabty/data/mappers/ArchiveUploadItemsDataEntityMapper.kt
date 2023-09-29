package com.neqabty.data.mappers

import com.neqabty.data.entities.ArchiveUploadItemData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ArchiveUploadItemEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArchiveUploadItemsDataEntityMapper @Inject constructor() : Mapper<ArchiveUploadItemData, ArchiveUploadItemEntity>() {

    override fun mapFrom(from: ArchiveUploadItemData): ArchiveUploadItemEntity {
        return ArchiveUploadItemEntity(
            id = from.id,
            name = from.name,
            description = from.description,
            categoryId = from.categoryId,
            doc1 = from.doc1
        )
    }
}
