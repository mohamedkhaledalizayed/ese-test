package com.neqabty.recruitment.modules.personalinfo.view.mappers

import com.neqabty.recruitment.modules.personalinfo.domain.entity.country.CountryEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.governement.GovernmentEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.maritalstatus.MaritalStatusEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.nationalities.NationalityEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.university.UniversityEntity
import com.neqabty.recruitment.modules.personalinfo.view.model.ItemUi


fun MaritalStatusEntity.toMaritalStatusItemUi(): ItemUi{
    return ItemUi(
        id = id,
        name = name
    )
}

fun NationalityEntity.toNationalityItemUi(): ItemUi{
    return ItemUi(
        id = id,
        name = name
    )
}

fun CountryEntity.toCountryItemUi(): ItemUi{
    return ItemUi(
        id = id,
        name = name
    )
}

fun GovernmentEntity.toGovernmentItemUi(): ItemUi{
    return ItemUi(
        id = id,
        name = name
    )
}

fun UniversityEntity.toUniversityItemUi(): ItemUi{
    return ItemUi(
        id = id,
        name = name
    )
}