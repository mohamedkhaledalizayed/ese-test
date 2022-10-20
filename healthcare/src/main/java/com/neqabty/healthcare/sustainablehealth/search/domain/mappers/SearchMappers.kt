package com.neqabty.healthcare.sustainablehealth.search.domain.mappers

import com.neqabty.healthcare.sustainablehealth.search.data.model.search.ServiceProviderType
import com.neqabty.healthcare.sustainablehealth.search.data.model.search.Area
import com.neqabty.healthcare.sustainablehealth.search.data.model.search.Degree
import com.neqabty.healthcare.sustainablehealth.search.data.model.search.Governorate
import com.neqabty.healthcare.sustainablehealth.search.data.model.search.Profession
import com.neqabty.healthcare.sustainablehealth.search.data.model.search.ProvidersModel
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.ServiceProviderTypeEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.AreaEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.DegreeEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.GovernorateEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.ProfessionEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.ProvidersEntity

fun ProvidersModel.toProvidersEntity(): ProvidersEntity{
    return ProvidersEntity(
        address = address,
        area =  area?.toAreaEntity(),
        degree = degree?.toDegreeEntity(),
        email = email,
        governorate = governorate.toGovernorateEntity(),
        id = id,
        image = image,
        name = name,
        phone = phone,
        profession = profession?.toProfessionEntity(),
        serviceProviderType = serviceProviderType?.toServiceTypeEntity()
    )
}

private fun ServiceProviderType.toServiceTypeEntity(): ServiceProviderTypeEntity {
    return ServiceProviderTypeEntity(
        id = id,
        providerTypeAr = providerTypeAr,
    )
}

private fun Profession.toProfessionEntity(): ProfessionEntity {
    return ProfessionEntity(
        id = id,
        professionName = professionName
    )
}

private fun Governorate.toGovernorateEntity(): GovernorateEntity {
    return GovernorateEntity(
        governorateAr = governorateAr,
        id = id
    )
}

private fun Degree.toDegreeEntity(): DegreeEntity {
    return DegreeEntity(
        degreeName = degreeName,
        id = id
    )
}

private fun Area.toAreaEntity(): AreaEntity {
    return AreaEntity(
        id = id,
        areaName = areaName
    )
}
