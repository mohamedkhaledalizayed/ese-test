package com.neqabty.data.mappers

import com.neqabty.data.entities.AdData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.AdEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdDataEntityMapper @Inject constructor() : Mapper<AdData, AdEntity>() {

    override fun mapFrom(from: AdData): AdEntity {
        return AdEntity(
            imgURL = from.imgURL,
            id = from.id
        )
    }
}
