package com.neqabty.recruitment.modules.personalinfo.domain.repository


import com.neqabty.recruitment.modules.personalinfo.domain.entity.country.CountryEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.governement.GovernmentEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.maritalstatus.MaritalStatusEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.nationalities.NationalityEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.university.UniversityEntity
import kotlinx.coroutines.flow.Flow

interface PersonalInfoRepository {
    fun getMaritalStatus(): Flow<List<MaritalStatusEntity>>
    fun getNationalities(): Flow<List<NationalityEntity>>
    fun getCountries(): Flow<List<CountryEntity>>
    fun getGovernorates(): Flow<List<GovernmentEntity>>
    fun getUniversities(): Flow<List<UniversityEntity>>

}