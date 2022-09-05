package com.neqabty.recruitment.modules.profile.data.source

import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.profile.data.api.ProfileApi
import com.neqabty.recruitment.modules.profile.data.model.*
import com.neqabty.recruitment.modules.profile.data.model.area.AreaModel
import com.neqabty.recruitment.modules.profile.data.model.companies.CompanyModel
import com.neqabty.recruitment.modules.profile.data.model.country.CountryModel
import com.neqabty.recruitment.modules.profile.data.model.cources.CourseModel
import com.neqabty.recruitment.modules.profile.data.model.department.DepartmentModel
import com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata.EngineerModel
import com.neqabty.recruitment.modules.profile.data.model.governement.GovernorateModel
import com.neqabty.recruitment.modules.profile.data.model.grades.GradeModel
import com.neqabty.recruitment.modules.profile.data.model.industries.IndustryModel
import com.neqabty.recruitment.modules.profile.data.model.language.LanguageModel
import com.neqabty.recruitment.modules.profile.data.model.maritalstatus.MaritalStatusModel
import com.neqabty.recruitment.modules.profile.data.model.militarystatus.MilitaryStatusModel
import com.neqabty.recruitment.modules.profile.data.model.nationalities.NationalityModel
import com.neqabty.recruitment.modules.profile.data.model.skills.SkillModel
import com.neqabty.recruitment.modules.profile.data.model.universities.UniversityModel
import javax.inject.Inject

class ProfileDS @Inject constructor(private val profileApi: ProfileApi) {

    suspend fun getCountries(): List<CountryModel>{
        return profileApi.getCountries().cities
    }

    suspend fun getGovernments(): List<GovernorateModel>{
        return profileApi.getGovernments().governorates
    }

    suspend fun getAreas(): List<AreaModel>{
        return profileApi.getAreas().zones
    }

    suspend fun getUniversities(): List<UniversityModel>{
        return profileApi.getUniversities().universities
    }

    suspend fun getDepartments(): List<DepartmentModel>{
        return profileApi.getDepartments().departments
    }

    suspend fun getLanguages(): List<LanguageModel>{
        return profileApi.getLanguages().languages
    }

    suspend fun getCourses(): List<CourseModel>{
        return profileApi.getCourses().courses
    }

    suspend fun getCompanies(): List<CompanyModel>{
        return profileApi.getCompanies().companies
    }

    suspend fun getSkills(): List<SkillModel>{
        return profileApi.getSkills().skills
    }

    suspend fun getGrades(): List<GradeModel>{
        return profileApi.getGrades().grades
    }

    suspend fun getNationalities(): List<NationalityModel>{
        return profileApi.getNationalities().nationalities
    }

    suspend fun getMilitaryStatus(): List<MilitaryStatusModel>{
        return profileApi.getMilitaryStatus().militarystatuses
    }

    suspend fun getMaritalStatus(): List<MaritalStatusModel>{
        return profileApi.getMaritalStatus().maritalstatuses
    }

    suspend fun getIndustries(): List<IndustryModel>{
        return profileApi.getIndustries().industries
    }








    suspend fun addCourse(courseBody: CourseBody): String {
        return profileApi.addCourse(courseBody).course.name
    }

    suspend fun addSkills(skillsBody: SkillsBody): String {
        return profileApi.addSkills(skillsBody).skill.name
    }

    suspend fun addUniversity(universityBody: UniversityBody): String {
        return profileApi.addUniversity(universityBody).university.name
    }

    suspend fun addArea(areaBody: AreaBody): String {
        return profileApi.addArea(areaBody).zone.name
    }

    suspend fun addLanguage(languageBody: LanguageBody): String {
        return profileApi.addLanguage(languageBody).language.name
    }

    suspend fun addEngineerLanguage(engineerLanguageBody: EngineerLanguageBody): String {
        return profileApi.addEngineerLanguage(engineerLanguageBody).engineerlanguage.engineer
    }

    suspend fun addEngineerSkills(engineerSkillsBody: EngineerSkillsBody): String {
        return profileApi.addEngineerSkills(engineerSkillsBody).engineerskill.engineer
    }

    suspend fun addEngineerExperience(engineerExperienceBody: EngineerExperienceBody): String {
        return profileApi.addEngineerExperience(engineerExperienceBody).experiences[0].engineer
    }

}