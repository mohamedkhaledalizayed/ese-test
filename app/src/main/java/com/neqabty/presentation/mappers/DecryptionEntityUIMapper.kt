package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.DecryptionEntity
import com.neqabty.presentation.entities.DecryptionUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DecryptionEntityUIMapper @Inject constructor() : Mapper<DecryptionEntity, DecryptionUI>() {

    override fun mapFrom(from: DecryptionEntity): DecryptionUI {
        return DecryptionUI(
                result = from.result
        )
    }
}
