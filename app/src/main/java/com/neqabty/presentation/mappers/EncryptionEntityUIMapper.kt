package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.EncryptionEntity
import com.neqabty.presentation.entities.EncryptionUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptionEntityUIMapper @Inject constructor() : Mapper<EncryptionEntity, EncryptionUI>() {

    override fun mapFrom(from: EncryptionEntity): EncryptionUI {
        return EncryptionUI(
                encryption = from.encryption
        )
    }
}
