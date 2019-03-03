package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.TripEntity
import com.neqabty.presentation.entities.TripUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripsEntityUIMapper @Inject constructor() : Mapper<TripEntity, TripUI>() {

    override fun mapFrom(from: TripEntity): TripUI {
        val tripUI= TripUI(
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
                price = from.price + " ج"
        )

        from.imgs?.let {
            val imgs: List<TripUI.TripImage> = it.map { imageItem ->
                return@map TripUI.TripImage(imageItem.imageId, imageItem.file, imageItem.tripId, imageItem.createdBy, imageItem.updatedBy, imageItem.createdAt, imageItem.updatedAt)
            }
            tripUI.imgs = imgs
        }
        return tripUI
    }
}