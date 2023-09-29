package com.neqabty.data.mappers

import com.neqabty.data.entities.ArchiveUploadAcknowledgementData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ArchiveUploadAcknowledgementEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArchiveUploadAcknowledgementDataEntityMapper @Inject constructor() : Mapper<ArchiveUploadAcknowledgementData, ArchiveUploadAcknowledgementEntity>() {

    override fun mapFrom(from: ArchiveUploadAcknowledgementData): ArchiveUploadAcknowledgementEntity {
        return ArchiveUploadAcknowledgementEntity(
            name = from.name,
            id = from.id,
            description = from.description,
            uploadTypeId = from.uploadTypeId,
            doc1 = from.doc1,
            userId = from.userId
        )
    }
}
