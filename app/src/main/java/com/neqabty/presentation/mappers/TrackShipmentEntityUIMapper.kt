package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.TrackShipmentEntity
import com.neqabty.presentation.entities.TrackShipmentUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackShipmentEntityUIMapper @Inject constructor() : Mapper<TrackShipmentEntity, TrackShipmentUI>() {

    override fun mapFrom(from: TrackShipmentEntity): TrackShipmentUI {
        return TrackShipmentUI(
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
