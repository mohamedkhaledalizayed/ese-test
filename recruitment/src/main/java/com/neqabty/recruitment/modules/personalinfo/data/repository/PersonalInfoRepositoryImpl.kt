package com.neqabty.recruitment.modules.personalinfo.data.repository

import com.neqabty.recruitment.modules.personalinfo.data.datasource.PersonalInfoDS
import com.neqabty.recruitment.modules.personalinfo.data.model.country.CountryModel
import com.neqabty.recruitment.modules.engineer.data.model.engineerdata.CityModel
import com.neqabty.recruitment.modules.engineer.data.model.engineerdata.SkillModel
import com.neqabty.recruitment.modules.personalinfo.data.model.governement.GovernorateModel
import com.neqabty.recruitment.modules.personalinfo.data.model.grades.GradeModel
import com.neqabty.recruitment.modules.personalinfo.data.model.industries.IndustryModel
import com.neqabty.recruitment.modules.personalinfo.data.model.maritalstatus.MaritalStatusModel
import com.neqabty.recruitment.modules.personalinfo.data.model.militarystatus.MilitaryStatusModel
import com.neqabty.recruitment.modules.personalinfo.data.model.nationalities.NationalityModel
import com.neqabty.recruitment.modules.personalinfo.data.model.universities.UniversityModel
import com.neqabty.recruitment.modules.engineer.domain.entity.engineerdata.CityEntity
import com.neqabty.recruitment.modules.personalinfo.domain.repository.PersonalInfoRepository
import com.neqabty.recruitment.modules.personalinfo.domain.entity.country.CountryEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.governement.GovernmentEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.grades.GradeEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.industries.IndustryEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.maritalstatus.MaritalStatusEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.militarystatus.MilitaryStatusEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.nationalities.NationalityEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.skills.SkillsEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.university.UniversityEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PersonalInfoRepositoryImpl @Inject constructor(private val personalInfoDS: PersonalInfoDS):
    PersonalInfoRepository {

    override fun getMaritalStatus(): Flow<List<MaritalStatusEntity>> {
        return flow {
            emit(personalInfoDS.getMaritalStatus().map { it.toMaritalStatusEntity() })
        }
    }

    override fun getNationalities(): Flow<List<NationalityEntity>> {
        return flow {
            emit(personalInfoDS.getNationalities().map { it.toNationalityEntity() })
        }
    }

    override fun getCountries(): Flow<List<CountryEntity>> {
        return flow {
            emit(personalInfoDS.getCountries().map { it.toCountryEntity() })
        }
    }

    override fun getGovernorates(): Flow<List<GovernmentEntity>> {
        return flow {
            emit(personalInfoDS.getGovernorates().map { it.toGovernmentEntity() })
        }
    }

    override fun getUniversities(): Flow<List<UniversityEntity>> {
        return flow {
            emit(personalInfoDS.getUniversities().map { it.toUniversityEntity() })
        }
    }

}


private fun CityModel.toCityEntity(): CityEntity {
    return CityEntity(
        id = id,
        name = name
    )
}

private fun SkillModel.toSkillsEntity(): SkillsEntity {
    return SkillsEntity(
        id = id,
        name = name,
        type = type
    )
}

private fun GradeModel.toGradeEntity(): GradeEntity {
    return GradeEntity(
        id = id,
        value = value
    )
}

private fun NationalityModel.toNationalityEntity(): NationalityEntity {
    return NationalityEntity(
        id = id,
        name = name
    )
}

private fun MilitaryStatusModel.toMilitaryStatusEntity(): MilitaryStatusEntity {
    return MilitaryStatusEntity(
        id = id,
        name = name
    )
}

private fun MaritalStatusModel.toMaritalStatusEntity(): MaritalStatusEntity {
    return MaritalStatusEntity(
        id = id,
        name = name
    )
}

private fun IndustryModel.toIndustryEntity(): IndustryEntity {
    return IndustryEntity(
        id = id,
        name = name
    )
}

private fun CountryModel.toCountryEntity(): CountryEntity {
    return CountryEntity(
        id = id,
        name = name
    )
}

private fun GovernorateModel.toGovernmentEntity(): GovernmentEntity {
    return GovernmentEntity(
        id = id,
        name = name
    )
}
//
//private fun AreaModel.toAreaEntity(): AreaEntity {
//    return AreaEntity(
//        id = id,
//        name = name
//    )
//}
//
private fun UniversityModel.toUniversityEntity(): UniversityEntity {
    return UniversityEntity(
        id = id,
        name = name
    )
}
//
//private fun DepartmentModel.toDepartmentEntity(): DepartmentEntity {
//    return DepartmentEntity(
//        id = id,
//        name = name
//    )
//}
//
//private fun LanguageModel.toLanguageEntity(): LanguageEntity {
//    return LanguageEntity(
//        id = id,
//        name = name
//    )
//}
//
//private fun CourseModel.toCourseEntity(): CourseEntity {
//    return CourseEntity(
//        id = id,
//        name = name
//    )
//}
//
//private fun CompanyModel.toCompanyEntity(): CompanyEntity {
//    return CompanyEntity(
//        about = about,
//        email = email,
//        established = established,
//        faxNumber = faxNumber,
//        headquarters = headquarters,
//        id = id,
//        linkedInLink = linkedInLink,
//        mobileNumber = mobileNumber,
//        name = name,
//        numberOfEmployees = numberOfEmployees,
//        ownerName = ownerName,
//        phoneNumber = phoneNumber,
//        website = website
//    )
//}