package com.neqabty.data.mappers

import com.neqabty.data.entities.TripData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.TripEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripsDataEntityMapper @Inject constructor() : Mapper<TripData, TripEntity>() {

    override fun mapFrom(from: TripData): TripEntity {
        val tripEntity = TripEntity(
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

        from.imgs?.let {
            val imgs: List<TripEntity.TripImage> = it.map { imageItem ->
                return@map TripEntity.TripImage(imageItem.imageId, imageItem.file, imageItem.tripId, imageItem.createdBy, imageItem.updatedBy, imageItem.createdAt, imageItem.updatedAt)
            }
            tripEntity.imgs = imgs
        }
        return tripEntity
    }
}
