package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.TripEntity
import com.neqabty.presentation.entities.TripUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripsEntityUIMapper @Inject constructor() : Mapper<TripEntity, TripUI>() {

    override fun mapFrom(from: TripEntity): TripUI {
        return TripUI(
                id = from.id,
                governId = from.governId,
                subSyndicateId = from.subSyndicateId,
                mainSyndicateId = from.mainSyndicateId,
                dateFrom = from.dateFrom,
                dateTo = from.dateTo,
                desc = from.desc,
                img = from.img,
                title = from.title,
                typeId = from.typeId
        )
    }
}