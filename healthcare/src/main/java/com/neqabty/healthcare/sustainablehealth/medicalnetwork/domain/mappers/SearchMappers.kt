package com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.mappers

import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search.ServiceProviderType
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search.Area
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search.Degree
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search.Governorate
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search.Profession
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search.ProvidersModel
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.ServiceProviderTypeEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.AreaEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.DegreeEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.GovernorateEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.ProfessionEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.ProvidersEntity

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
        providerTypeEn = providerTypeEn
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
