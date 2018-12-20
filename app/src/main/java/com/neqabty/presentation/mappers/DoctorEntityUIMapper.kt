package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.DoctorEntity
import com.neqabty.presentation.entities.DoctorUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoctorEntityUIMapper @Inject constructor() : Mapper<DoctorEntity, DoctorUI>() {

    override fun mapFrom(from: DoctorEntity): DoctorUI {
        return DoctorUI(
                id = from.id,
                address = from.address,
                area = from.area,
                areaCode = from.areaCode,
                category = from.category,
                degree = from.degree,
                degreeCode = from.degreeCode,
                name = from.name,
                phoneCode = from.phoneCode,
                phoneNumber = from.phoneNumber,
                profissionCode = from.profissionCode,
                serial = from.serial
        )
    }
}
