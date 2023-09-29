package com.neqabty.data.mappers

import com.neqabty.data.entities.ArchiveUploadCategoryData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ArchiveUploadCategoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArchiveUploadCategoryDataEntityMapper @Inject constructor() : Mapper<ArchiveUploadCategoryData, ArchiveUploadCategoryEntity>() {

    override fun mapFrom(from: ArchiveUploadCategoryData): ArchiveUploadCategoryEntity {
        return ArchiveUploadCategoryEntity(
            id = from.id, name = from.name
        )
    }
}
