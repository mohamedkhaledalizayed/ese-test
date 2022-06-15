package com.neqabty.healthcare.modules.search.data.model.mappers

import com.neqabty.healthcare.modules.search.data.model.*
import com.neqabty.healthcare.modules.search.domain.entity.*

fun MedicalProviderModel.toMedicalProviderEntity(): MedicalProviderEntity {
    return MedicalProviderEntity(
        addedBy = addedBy ?: "",
        address = address,
        areaEntity = area?.toAreaEntity() ?: Area().toAreaEntity(),
        areaId = areaId,
        createdAt = createdAt?.time ?: 0L,
        degreeEntity = degree?.toDegreeEntity() ?: Degree().toDegreeEntity(),
        degreeId = degreeId,
        deletedAt = deletedAt?.time ?: 0L,
        email = email,
        governorateEntity = governorate?.toGovEntity() ?: Governorate().toGovEntity(),
        governorateId = governorateId,
        id = id,
        image = image ?: "",
        name = name,
        phone = phone,
        professionEntity = profession?.toProEntity() ?: Profession().toProEntity(),
        professionId = professionId,
        serviceProviderTypeEntity = serviceProviderType?.toServiceTypeEntity() ?: ServiceProviderType().toServiceTypeEntity(),
        serviceProviderTypeId = serviceProviderTypeId,
        updatedAt = updatedAt?.time ?: 0L
    )
}

private fun ServiceProviderType.toServiceTypeEntity(): ServiceProviderTypeEntity {
    return ServiceProviderTypeEntity(
        createdAt = createdAt?.time ?: 0L,
        id = id,
        points = points?: "",
        providerTypeAr = providerTypeAr,
        providerTypeEn = providerTypeEn,
        updatedAt = updatedAt?.time ?: 0L
    )
}

private fun Profession.toProEntity(): ProfessionEntity {
    return ProfessionEntity(
        createdAt = createdAt?.time ?: 0L,
        id = id,
        professionName = professionName,
        updatedAt = updatedAt?.time ?: 0L
    )
}

private fun Governorate.toGovEntity(): GovernorateEntity {
    return GovernorateEntity(
        createdAt = createdAt?.time ?: 0L,
        governorateAr = governorateAr,
        governorateEn = governorateEn,
        id = id,
        updatedAt = updatedAt?.time ?: 0L
    )
}

private fun Degree.toDegreeEntity(): DegreeEntity {
    return DegreeEntity(
        createdAt = createdAt?.time ?: 0L,
        degreeName = degreeName,
        id = id,
        updatedAt = updatedAt?.time ?: 0L
    )
}

private fun Area.toAreaEntity(): AreaEntity {
    return AreaEntity(
        areaName = areaName,
        createdAt = createdAt?.time ?: 0L,
        governorateId = governorateId,
        id = id,
        updatedAt = updatedAt?.time ?: 0L
    )
}
