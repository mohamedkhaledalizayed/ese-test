package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.TripEntity
import com.neqabty.presentation.entities.TripUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripsEntityUIMapper @Inject constructor() : Mapper<TripEntity, TripUI>() {

    override fun mapFrom(from: TripEntity): TripUI {
        var tripUI = TripUI(
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
            var regiments: List<TripUI.TripRegiment> = it.map { regimentItem ->
                return@map TripUI.TripRegiment(
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
            tripUI.regiments = regiments
        }
        from.place?.let {
            var place: TripUI.TripPlace = TripUI.TripPlace(it.placeId, it.details, it.name)
            tripUI.place = place
        }
        return tripUI
    }
}