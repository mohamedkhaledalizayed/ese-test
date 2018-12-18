package com.neqabty.data.mappers

import com.neqabty.data.entities.TripData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.TripEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripsDataEntityMapper @Inject constructor() : Mapper<TripData, TripEntity>() {

    override fun mapFrom(from: TripData): TripEntity {
        return TripEntity(
                id = from.id,
                governId = from.governId,
                subSyndicateId = from.subSyndicateId,
                mainSyndicateId = from.mainSyndicateId,
                dateFrom = from.dateFrom,
                dateTo = from.dateTo,
                desc = from.desc,
                img = from.img,
                title = from.title,
                typeId = from.typeId,
                price = from.price
        )
    }
}
