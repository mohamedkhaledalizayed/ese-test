package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.OnlinePharmacyEntity
import com.neqabty.presentation.entities.OnlinePharmacyUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlinePharmacyURLEntityUIMapper @Inject constructor() : Mapper<OnlinePharmacyEntity, OnlinePharmacyUI>() {

    override fun mapFrom(from: OnlinePharmacyEntity): OnlinePharmacyUI {
        return OnlinePharmacyUI(
                type = from.type,
                url = from.url
        )
    }
}
