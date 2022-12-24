package com.neqabty.recruitment.modules.personalinfo.domain.usecases



import com.neqabty.recruitment.modules.personalinfo.domain.entity.country.CountryEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.governement.GovernmentEntity
import com.neqabty.recruitment.modules.personalinfo.domain.repository.PersonalInfoRepository
import com.neqabty.recruitment.modules.personalinfo.domain.entity.maritalstatus.MaritalStatusEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.nationalities.NationalityEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.university.UniversityEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonalInfoUseCase @Inject constructor(private val personalInfoRepository: PersonalInfoRepository){

    fun getMaritalStatus(): Flow<List<MaritalStatusEntity>>{
        return personalInfoRepository.getMaritalStatus()
    }

    fun getNationalities(): Flow<List<NationalityEntity>>{
        return personalInfoRepository.getNationalities()
    }

    fun getCountries(): Flow<List<CountryEntity>>{
        return personalInfoRepository.getCountries()
    }

    fun getGovernorates(): Flow<List<GovernmentEntity>>{
        return personalInfoRepository.getGovernorates()
    }

    fun getUniversities(): Flow<List<UniversityEntity>>{
        return personalInfoRepository.getUniversities()
    }

}