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
            price = from.price,
            notes = from.notes,
            imgs = from.imgs
        )

        from.regiments?.let {
            var regiments: List<TripEntity.TripRegiment> = it.map { regimentItem ->
                return@map TripEntity.TripRegiment(
                    regimentItem.regimentId,
                    regimentItem.tripId,
                    regimentItem.dateFrom,
                    regimentItem.dateTo,
                    regimentItem.hotelOnePerson,
                    regimentItem.hotelTwoPerson,
                    regimentItem.hotelThreePerson,
                    regimentItem.viewPrice,
                    regimentItem.sidePrice,
                    regimentItem.price,
                    regimentItem.oneRoom,
                    regimentItem.twoRooms,
                    regimentItem.studio,
                    regimentItem.tripType,
                    regimentItem.createdBy,
                    regimentItem.updatedBy,
                    regimentItem.createdAt,
                    regimentItem.updatedAt
                )
            }
            tripEntity.regiments = regiments
        }
        from.place?.let {
            var place: TripEntity.TripPlace = TripEntity.TripPlace(it.placeId, it.details, it.name)
            tripEntity.place = place
        }
        return tripEntity
    }
}
