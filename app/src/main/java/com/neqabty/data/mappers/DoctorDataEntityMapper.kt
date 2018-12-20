package com.neqabty.data.mappers

import com.neqabty.data.entities.DoctorData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.DoctorEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoctorDataEntityMapper @Inject constructor() : Mapper<DoctorData, DoctorEntity>() {

    override fun mapFrom(from: DoctorData): DoctorEntity {
        return DoctorEntity(
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
