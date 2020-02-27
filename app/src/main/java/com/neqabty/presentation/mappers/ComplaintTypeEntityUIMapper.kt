package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ComplaintTypeEntity
import com.neqabty.presentation.entities.ComplaintTypeUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComplaintTypeEntityUIMapper @Inject constructor() : Mapper<ComplaintTypeEntity, ComplaintTypeUI>() {

    override fun mapFrom(from: ComplaintTypeEntity): ComplaintTypeUI {
        return ComplaintTypeUI(
                id = from.id,
                type = from.type
        )
    }
}
