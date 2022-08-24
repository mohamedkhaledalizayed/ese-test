package com.neqabty.recruitment.modules.profile.data.repository

import com.neqabty.recruitment.modules.profile.data.model.*
import com.neqabty.recruitment.modules.profile.data.model.area.AreaModel
import com.neqabty.recruitment.modules.profile.data.model.companies.CompanyModel
import com.neqabty.recruitment.modules.profile.data.model.country.CountryModel
import com.neqabty.recruitment.modules.profile.data.model.cources.CourseModel
import com.neqabty.recruitment.modules.profile.data.model.department.DepartmentModel
import com.neqabty.recruitment.modules.profile.data.model.engineerdata.CityModel
import com.neqabty.recruitment.modules.profile.data.model.engineerdata.EngineerModel
import com.neqabty.recruitment.modules.profile.data.model.governement.GovernorateModel
import com.neqabty.recruitment.modules.profile.data.model.grades.GradeModel
import com.neqabty.recruitment.modules.profile.data.model.industries.IndustryModel
import com.neqabty.recruitment.modules.profile.data.model.language.LanguageModel
import com.neqabty.recruitment.modules.profile.data.model.maritalstatus.MaritalStatusModel
import com.neqabty.recruitment.modules.profile.data.model.militarystatus.MilitaryStatusModel
import com.neqabty.recruitment.modules.profile.data.model.nationalities.NationalityModel
import com.neqabty.recruitment.modules.profile.data.model.skills.SkillModel
import com.neqabty.recruitment.modules.profile.data.model.universities.UniversityModel
import com.neqabty.recruitment.modules.profile.data.source.ProfileDS
import com.neqabty.recruitment.modules.profile.domain.*
import com.neqabty.recruitment.modules.profile.domain.entity.area.AreaEntity
import com.neqabty.recruitment.modules.profile.domain.entity.company.CompanyEntity
import com.neqabty.recruitment.modules.profile.domain.entity.country.CountryEntity
import com.neqabty.recruitment.modules.profile.domain.entity.courses.CourseEntity
import com.neqabty.recruitment.modules.profile.domain.entity.department.DepartmentEntity
import com.neqabty.recruitment.modules.profile.domain.entity.engineerdata.CityEntity
import com.neqabty.recruitment.modules.profile.domain.entity.engineerdata.EngineerEntity
import com.neqabty.recruitment.modules.profile.domain.entity.governement.GovernmentEntity
import com.neqabty.recruitment.modules.profile.domain.entity.grades.GradeEntity
import com.neqabty.recruitment.modules.profile.domain.entity.industries.IndustryEntity
import com.neqabty.recruitment.modules.profile.domain.entity.language.LanguageEntity
import com.neqabty.recruitment.modules.profile.domain.entity.maritalstatus.MaritalStatusEntity
import com.neqabty.recruitment.modules.profile.domain.entity.militarystatus.MilitaryStatusEntity
import com.neqabty.recruitment.modules.profile.domain.entity.nationalities.NationalityEntity
import com.neqabty.recruitment.modules.profile.domain.entity.skills.SkillsEntity
import com.neqabty.recruitment.modules.profile.domain.entity.university.UniversityEntity
import com.neqabty.recruitment.modules.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileDS: ProfileDS): ProfileRepository {
    override fun getCountries(): Flow<List<CountryEntity>> {
        return flow {
            emit(profileDS.getCountries().map { it.toCountryEntity() })
        }
    }

    override fun getGovernments(): Flow<List<GovernmentEntity>> {
        return flow {
            emit(profileDS.getGovernments().map { it.toGovernmentEntity() })
        }
    }

    override fun getAreas(): Flow<List<AreaEntity>> {
        return flow {
            emit(profileDS.getAreas().map { it.toAreaEntity() })
        }
    }

    override fun getUniversities(): Flow<List<UniversityEntity>> {
        return flow {
            emit(profileDS.getUniversities().map { it.toUniversityEntity() })
        }
    }

    override fun getDepartments(): Flow<List<DepartmentEntity>> {
        return flow {
            emit(profileDS.getDepartments().map { it.toDepartmentEntity() })
        }
    }

    override fun getLanguages(): Flow<List<LanguageEntity>> {
        return flow {
            emit(profileDS.getLanguages().map { it.toLanguageEntity() })
        }
    }

    override fun getCourses(): Flow<List<CourseEntity>> {
        return flow {
            emit(profileDS.getCourses().map { it.toCourseEntity() })
        }
    }

    override fun getCompanies(): Flow<List<CompanyEntity>> {
        return flow {
            emit(profileDS.getCompanies().map { it.toCompanyEntity() })
        }
    }

    override fun getSkills(): Flow<List<SkillsEntity>> {
        return flow {
            emit(profileDS.getSkills().map { it.toSkillsEntity() })
        }
    }

    override fun getGrades(): Flow<List<GradeEntity>> {
        return flow {
            emit(profileDS.getGrades().map { it.toGradeEntity() })
        }
    }

    override fun getNationalities(): Flow<List<NationalityEntity>> {
        return flow {
            emit(profileDS.getNationalities().map { it.toNationalityEntity() })
        }
    }

    override fun getMilitaryStatus(): Flow<List<MilitaryStatusEntity>> {
        return flow {
            emit(profileDS.getMilitaryStatus().map { it.toMilitaryStatusEntity() })
        }
    }

    override fun getMaritalStatus(): Flow<List<MaritalStatusEntity>> {
        return flow {
            emit(profileDS.getMaritalStatus().map { it.toMaritalStatusEntity() })
        }
    }

    override fun getIndustries(): Flow<List<IndustryEntity>> {
        return flow {
            emit(profileDS.getIndustries().map { it.toIndustryEntity() })
        }
    }

    override fun getEngineerData(): Flow<EngineerEntity> {
        return flow {
            emit(profileDS.getEngineerData().toEngineerEntity())
        }
    }

    override fun addCourse(name: String): Flow<String> {
        return flow {
            emit(profileDS.addCourse(CourseBody(Course(name))))
        }
    }

    override fun addSkills(name: String, type: String): Flow<String> {
        return flow {
            emit(profileDS.addSkills(SkillsBody(Skill(name, type))))
        }
    }

    override fun addUniversity(name: String): Flow<String> {
        return flow {
            emit(profileDS.addUniversity(UniversityBody(University(name))))
        }
    }

    override fun addArea(name: String): Flow<String> {
        return flow {
            emit(profileDS.addArea(AreaBody(Zone(name))))
        }
    }

    override fun addLanguage(name: String): Flow<String> {
        return flow {
            emit(profileDS.addLanguage(LanguageBody(Language(name))))
        }
    }

    override fun addEngineerLanguage(engineer: Int, language: Int, level: String): Flow<String> {
        return flow {
            emit(profileDS.addEngineerLanguage(EngineerLanguageBody(EngineerLanguage(engineer, language, level))))
        }
    }

    override fun addEngineerSkills(engineer: Int, skill: Int): Flow<String> {
        return flow {
            emit(profileDS.addEngineerSkills(EngineerSkillsBody(Engineerskill(engineer, skill))))
        }
    }

    override fun addEngineerExperience(engineerExperienceBody: EngineerExperienceBody): Flow<String> {
        return flow {
            emit(profileDS.addEngineerExperience(engineerExperienceBody))
        }
    }
}

private fun CountryModel.toCountryEntity(): CountryEntity{
    return CountryEntity(
        id = id,
        name = name
    )
}

private fun GovernorateModel.toGovernmentEntity(): GovernmentEntity{
    return GovernmentEntity(
        id = id,
        name = name
    )
}

private fun AreaModel.toAreaEntity(): AreaEntity{
    return AreaEntity(
        id = id,
        name = name
    )
}

private fun UniversityModel.toUniversityEntity(): UniversityEntity{
    return UniversityEntity(
        id = id,
        name = name
    )
}

private fun DepartmentModel.toDepartmentEntity(): DepartmentEntity{
    return DepartmentEntity(
        id = id,
        name = name
    )
}

private fun LanguageModel.toLanguageEntity(): LanguageEntity{
    return LanguageEntity(
        id = id,
        name = name
    )
}

private fun CourseModel.toCourseEntity(): CourseEntity{
    return CourseEntity(
        id = id,
        name = name
    )
}

private fun CompanyModel.toCompanyEntity(): CompanyEntity{
    return CompanyEntity(
        about = about,
        email = email,
        established = established,
        faxNumber = faxNumber,
        headquarters = headquarters,
        id = id,
        linkedInLink = linkedInLink,
        mobileNumber = mobileNumber,
        name = name,
        numberOfEmployees = numberOfEmployees,
        ownerName = ownerName,
        phoneNumber = phoneNumber,
        website = website
    )
}

private fun SkillModel.toSkillsEntity(): SkillsEntity{
    return SkillsEntity(
        id = id,
        name = name,
        type = type
    )
}

private fun GradeModel.toGradeEntity(): GradeEntity{
    return GradeEntity(
        id = id,
        value = value
    )
}

private fun NationalityModel.toNationalityEntity(): NationalityEntity{
    return NationalityEntity(
        id = id,
        name = name
    )
}

private fun MilitaryStatusModel.toMilitaryStatusEntity(): MilitaryStatusEntity{
    return MilitaryStatusEntity(
        id = id,
        name = name
    )
}

private fun MaritalStatusModel.toMaritalStatusEntity(): MaritalStatusEntity{
    return MaritalStatusEntity(
        id = id,
        name = name
    )
}

private fun IndustryModel.toIndustryEntity(): IndustryEntity{
    return IndustryEntity(
        id = id,
        name = name
    )
}

private fun EngineerModel.toEngineerEntity(): EngineerEntity{
    return EngineerEntity(
        address = address,
        city = city.toCityEntity(),
        courses = courses.map { it.toCourse() },
        dateOfBirth = dateOfBirth,
        department = department.toDepartment(),
        educations = educations.map { it.toEducationEntity() },
        email = email,
        experiences = experiences.map { it.toExperienceEntity() },
        gender = gender,
        governorate = governorate.toGovernment(),
        grade =grade,
        graduationYear = graduationYear,
        languages = languages.map { it.toLanguage() },
        linkedInLink = linkedInLink,
        maritalStatus =  maritalStatus.toMaritalStatus(),
        membershipId = membershipId,
        membershipIdFile = membershipIdFile,
        militaryFile = militaryFile,
        militaryStatus = militaryStatus.toMilitaryStatus(),
        mobile = mobile,
        name = name,
        nationalId = nationalId,
        nationalIdFile = nationalIdFile,
        nationality = nationality.toNationality(),
        phone = phone,
        skills = skills.map { it.toSkills() },
        university = university.toUniversity(),
        workingStatus = workingStatus,
        yearsOfExperience = yearsOfExperience,
        zone = zone.toArea()
    )
}

private fun CityModel.toCityEntity(): CityEntity{
    return CityEntity(
        id = id,
        name = name
    )
}

