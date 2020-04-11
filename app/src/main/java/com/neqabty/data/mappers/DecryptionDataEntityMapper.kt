package com.neqabty.data.mappers

import com.neqabty.data.entities.DecryptionData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.DecryptionEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DecryptionDataEntityMapper @Inject constructor() : Mapper<DecryptionData, DecryptionEntity>() {

    override fun mapFrom(from: DecryptionData): DecryptionEntity {
        return DecryptionEntity(
            result = from.result
        )
    }
}
