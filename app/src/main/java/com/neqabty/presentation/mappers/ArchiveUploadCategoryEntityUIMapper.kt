package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ArchiveUploadCategoryEntity
import com.neqabty.presentation.entities.ArchiveUploadCategoryUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArchiveUploadCategoryEntityUIMapper @Inject constructor() : Mapper<ArchiveUploadCategoryEntity, ArchiveUploadCategoryUI>() {

    override fun mapFrom(from: ArchiveUploadCategoryEntity): ArchiveUploadCategoryUI {
        return ArchiveUploadCategoryUI(
            id = from.id, name = from.name
        )
    }
}
