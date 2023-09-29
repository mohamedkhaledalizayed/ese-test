package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ArchiveUploadAcknowledgementEntity
import com.neqabty.presentation.entities.ArchiveUploadAcknowledgementUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArchiveUploadAcknowledgementEntityUIMapper @Inject constructor() : Mapper<ArchiveUploadAcknowledgementEntity, ArchiveUploadAcknowledgementUI>() {

    override fun mapFrom(from: ArchiveUploadAcknowledgementEntity): ArchiveUploadAcknowledgementUI {
        return ArchiveUploadAcknowledgementUI(
            name = from.name,
            id = from.id,
            description = from.description,
            uploadTypeId = from.uploadTypeId,
            doc1 = from.doc1,
            userId = from.userId
        )
    }
}
