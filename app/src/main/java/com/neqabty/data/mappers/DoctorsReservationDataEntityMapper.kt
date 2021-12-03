package com.neqabty.data.mappers

import com.neqabty.data.entities.DoctorsReservationData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.DoctorsReservationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoctorsReservationDataEntityMapper @Inject constructor() : Mapper<DoctorsReservationData, DoctorsReservationEntity>() {

    override fun mapFrom(from: DoctorsReservationData): DoctorsReservationEntity {
        return DoctorsReservationEntity(
            authCode = from.authCode
        )
    }
}
