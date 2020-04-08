package com.neqabty.data.mappers

import com.neqabty.data.entities.EncryptionData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.EncryptionEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptionDataEntityMapper @Inject constructor() : Mapper<EncryptionData, EncryptionEntity>() {

    override fun mapFrom(from: EncryptionData): EncryptionEntity {
        return EncryptionEntity(
            encryption = from.encryption
        )
    }
}
