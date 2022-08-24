package com.neqabty.recruitment.modules.profile.domain.usecase



import com.neqabty.recruitment.modules.profile.data.model.EngineerExperienceBody
import com.neqabty.recruitment.modules.profile.domain.entity.area.AreaEntity
import com.neqabty.recruitment.modules.profile.domain.entity.company.CompanyEntity
import com.neqabty.recruitment.modules.profile.domain.entity.country.CountryEntity
import com.neqabty.recruitment.modules.profile.domain.entity.courses.CourseEntity
import com.neqabty.recruitment.modules.profile.domain.entity.department.DepartmentEntity
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
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository){

    fun getCountries(): Flow<List<CountryEntity>>{
        return profileRepository.getCountries()
    }

    fun getGovernments(): Flow<List<GovernmentEntity>>{
        return profileRepository.getGovernments()
    }

    fun getAreas(): Flow<List<AreaEntity>>{
        return profileRepository.getAreas()
    }

    fun getUniversities(): Flow<List<UniversityEntity>>{
        return profileRepository.getUniversities()
    }

    fun getDepartments(): Flow<List<DepartmentEntity>>{
        return profileRepository.getDepartments()
    }

    fun getLanguages(): Flow<List<LanguageEntity>>{
        return profileRepository.getLanguages()
    }

    fun getCourses(): Flow<List<CourseEntity>>{
        return profileRepository.getCourses()
    }

    fun getCompanies(): Flow<List<CompanyEntity>>{
        return profileRepository.getCompanies()
    }

    fun getSkills(): Flow<List<SkillsEntity>>{
        return profileRepository.getSkills()
    }

    fun getGrades(): Flow<List<GradeEntity>>{
        return profileRepository.getGrades()
    }

    fun getNationalities(): Flow<List<NationalityEntity>>{
        return profileRepository.getNationalities()
    }

    fun getMilitaryStatus(): Flow<List<MilitaryStatusEntity>>{
        return profileRepository.getMilitaryStatus()
    }

    fun getMaritalStatus(): Flow<List<MaritalStatusEntity>>{
        return profileRepository.getMaritalStatus()
    }

    fun getIndustries(): Flow<List<IndustryEntity>>{
        return profileRepository.getIndustries()
    }

    fun getEngineerData(): Flow<EngineerEntity> {
        return profileRepository.getEngineerData()
    }




    fun addCourse(name: String): Flow<String> {
        return profileRepository.addCourse(name)
    }

    fun addSkills(name: String, type: String): Flow<String> {
        return profileRepository.addSkills(name, type)
    }

    fun addUniversity(name: String): Flow<String> {
        return profileRepository.addUniversity(name)
    }

    fun addArea(name: String): Flow<String> {
        return profileRepository.addArea(name)
    }

    fun addLanguage(name: String): Flow<String> {
        return profileRepository.addLanguage(name)
    }

    fun addEngineerLanguage(engineer: Int, language: Int, level: String): Flow<String> {
        return profileRepository.addEngineerLanguage(engineer, language, level)
    }

    fun addEngineerSkills(engineer: Int, skill: Int): Flow<String> {
        return profileRepository.addEngineerSkills(engineer, skill)
    }

    fun addEngineerExperience(engineerExperienceBody: EngineerExperienceBody): Flow<String> {
        return profileRepository.addEngineerExperience(engineerExperienceBody)
    }

}