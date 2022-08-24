package com.neqabty.recruitment.modules.profile.domain.repository

import com.neqabty.recruitment.modules.profile.data.model.EngineerExperienceBody
import com.neqabty.recruitment.modules.profile.data.model.EngineerLanguageBody
import com.neqabty.recruitment.modules.profile.data.model.EngineerSkillsBody
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
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getCountries(): Flow<List<CountryEntity>>
    fun getGovernments(): Flow<List<GovernmentEntity>>
    fun getAreas(): Flow<List<AreaEntity>>
    fun getUniversities(): Flow<List<UniversityEntity>>
    fun getDepartments(): Flow<List<DepartmentEntity>>
    fun getLanguages(): Flow<List<LanguageEntity>>
    fun getCourses(): Flow<List<CourseEntity>>
    fun getCompanies(): Flow<List<CompanyEntity>>
    fun getSkills(): Flow<List<SkillsEntity>>
    fun getGrades(): Flow<List<GradeEntity>>
    fun getNationalities(): Flow<List<NationalityEntity>>
    fun getMilitaryStatus(): Flow<List<MilitaryStatusEntity>>
    fun getMaritalStatus(): Flow<List<MaritalStatusEntity>>
    fun getIndustries(): Flow<List<IndustryEntity>>
    fun getEngineerData(): Flow<EngineerEntity>
    fun addCourse(name: String): Flow<String>
    fun addSkills(name: String, type: String): Flow<String>
    fun addUniversity(name: String): Flow<String>
    fun addArea(name: String): Flow<String>
    fun addLanguage(name: String): Flow<String>
    fun addEngineerLanguage(engineer: Int, language: Int, level: String): Flow<String>
    fun addEngineerSkills(engineer: Int, skill: Int): Flow<String>
    fun addEngineerExperience(engineerExperienceBody: EngineerExperienceBody): Flow<String>
}