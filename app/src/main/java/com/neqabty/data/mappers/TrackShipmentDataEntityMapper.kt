package com.neqabty.data.mappers

import com.neqabty.data.entities.TrackShipmentData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.TrackShipmentEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackShipmentDataEntityMapper @Inject constructor() : Mapper<TrackShipmentData, TrackShipmentEntity>() {

    override fun mapFrom(from: TrackShipmentData): TrackShipmentEntity {
        return TrackShipmentEntity(
                name = from.name,
                address = from.address,
                status = from.status,
                city = from.city,
                date = from.date,
                phone = from.phone,
                shipmentProvider = from.shipmentProvider,
                userNumber = from.userNumber
        )
    }
}
