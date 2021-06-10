package com.neqabty.data.mappers

import com.neqabty.data.entities.OnlinePharmacyData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.OnlinePharmacyEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlinePharmacyURLDataEntityMapper @Inject constructor() : Mapper<OnlinePharmacyData, OnlinePharmacyEntity>() {

    override fun mapFrom(from: OnlinePharmacyData): OnlinePharmacyEntity {
        return OnlinePharmacyEntity(
                type = from.type,
                url = from.url
        )
    }
}
