package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.AdEntity
import com.neqabty.presentation.entities.AdUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdEntityUIMapper @Inject constructor() : Mapper<AdEntity, AdUI>() {

    override fun mapFrom(from: AdEntity): AdUI {
        return AdUI(
                imgURL = from.imgURL,
                id = from.id
        )
    }
}
