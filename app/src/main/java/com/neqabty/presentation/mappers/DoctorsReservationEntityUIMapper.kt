package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.DoctorsReservationEntity
import com.neqabty.presentation.entities.DoctorsReservationUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoctorsReservationEntityUIMapper @Inject constructor() :
    Mapper<DoctorsReservationEntity, DoctorsReservationUI>() {

    override fun mapFrom(from: DoctorsReservationEntity): DoctorsReservationUI {
        return DoctorsReservationUI(
            authCode = from.authCode
        )
    }
}
