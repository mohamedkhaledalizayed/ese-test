package com.neqabty.healthcare.medicalnetwork.domain.mappers

import com.neqabty.healthcare.medicalnetwork.data.model.search.*
import com.neqabty.healthcare.medicalnetwork.domain.entity.search.*

fun ProvidersModel.toProvidersEntity(): ProvidersEntity{
    return ProvidersEntity(
        address = address,
        price = price ?: "غير محدد",
        area =  area?.toAreaEntity(),
        degree = degree?.toDegreeEntity(),
        email = email,
        governorate = governorate.toGovernorateEntity(),
        id = id,
        image = image,
        name = name,
        notes = notes ?: "لا يوجد",
        hasQR = hasQR == 1,
        phone = phone ?: "لا يوجد",
        mobile = mobile ?: "لا يوجد",
        profession = profession?.toProfessionEntity(),
        serviceProviderType = serviceProviderType?.toServiceTypeEntity()
    )
}

private fun ServiceProviderType.toServiceTypeEntity(): ServiceProviderTypeEntity {
    return ServiceProviderTypeEntity(
        id = id,
        providerTypeAr = providerTypeAr,
        providerTypeEn = providerTypeEn ?: ""
    )
}

private fun Profession.toProfessionEntity(): ProfessionEntity {
    return ProfessionEntity(
        id = id,
        professionName = professionName ?: "غير محدد"
    )
}

private fun Governorate.toGovernorateEntity(): GovernorateEntity {
    return GovernorateEntity(
        governorateAr = governorateAr ?: "",
        id = id
    )
}

private fun Degree.toDegreeEntity(): DegreeEntity {
    return DegreeEntity(
        degreeName = degreeName ?: "غير محدد",
        id = id
    )
}

private fun Area.toAreaEntity(): AreaEntity {
    return AreaEntity(
        id = id,
        areaName = areaName ?: ""
    )
}
