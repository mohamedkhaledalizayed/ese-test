package com.neqabty.recruitment.modules.profile.data.api

import com.neqabty.recruitment.modules.profile.data.model.*
import com.neqabty.recruitment.modules.profile.data.model.addarea.AddAreaModel
import com.neqabty.recruitment.modules.profile.data.model.addcourse.AddCourseModel
import com.neqabty.recruitment.modules.profile.data.model.addlanguage.AddLanguageModel
import com.neqabty.recruitment.modules.profile.data.model.addskills.AddSkillsModel
import com.neqabty.recruitment.modules.profile.data.model.adduniversity.AddUniversityModel
import com.neqabty.recruitment.modules.profile.data.model.area.AreaModelList
import com.neqabty.recruitment.modules.profile.data.model.companies.CompaniesModelList
import com.neqabty.recruitment.modules.profile.data.model.country.CountryModelList
import com.neqabty.recruitment.modules.profile.data.model.cources.CoursesModelList
import com.neqabty.recruitment.modules.profile.data.model.department.DepartmentModelList
import com.neqabty.recruitment.modules.profile.data.model.engineerdata.EngineerDataModel
import com.neqabty.recruitment.modules.profile.data.model.governement.GovernmentListModel
import com.neqabty.recruitment.modules.profile.data.model.grades.GradesModelList
import com.neqabty.recruitment.modules.profile.data.model.industries.IndustriesModelList
import com.neqabty.recruitment.modules.profile.data.model.language.LanguageModelList
import com.neqabty.recruitment.modules.profile.data.model.maritalstatus.MaritalStatusModelList
import com.neqabty.recruitment.modules.profile.data.model.militarystatus.MilitaeyStatusModelList
import com.neqabty.recruitment.modules.profile.data.model.nationalities.NationalitiesModelList
import com.neqabty.recruitment.modules.profile.data.model.skills.SkillsModelList
import com.neqabty.recruitment.modules.profile.data.model.universities.UniversityLisModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileApi {

    @GET("templatefields/cities")
    suspend fun getCountries(): CountryModelList

    @GET("templatefields/governorates")
    suspend fun getGovernments(): GovernmentListModel

    @GET("templatefields/zones")
    suspend fun getAreas(): AreaModelList

    @GET("templatefields/universities")
    suspend fun getUniversities(): UniversityLisModel

    @GET("templatefields/departments")
    suspend fun getDepartments(): DepartmentModelList

    @GET("templatefields/languages")
    suspend fun getLanguages(): LanguageModelList

    @GET("templatefields/courses")
    suspend fun getCourses(): CoursesModelList

    @GET("templatefields/skills")
    suspend fun getSkills(): SkillsModelList

    @GET("templatefields/grades")
    suspend fun getGrades(): GradesModelList

    @GET("templatefields/nationalities")
    suspend fun getNationalities(): NationalitiesModelList

    @GET("templatefields/militarystatus")
    suspend fun getMilitaryStatus(): MilitaeyStatusModelList

    @GET("templatefields/maritalstatus")
    suspend fun getMaritalStatus(): MaritalStatusModelList

    @GET("templatefields/industries")
    suspend fun getIndustries(): IndustriesModelList

    @GET("company/companies")
    suspend fun getCompanies(): CompaniesModelList

    @GET("engineer/engineers")
    suspend fun getEngineerData(): EngineerDataModel










    @POST("templatefields/courses")
    suspend fun addCourse(@Body courseBody: CourseBody): AddCourseModel

    @POST("templatefields/skills")
    suspend fun addSkills(@Body skillsBody: SkillsBody): AddSkillsModel

    @POST("templatefields/universities")
    suspend fun addUniversity(@Body universityBody: UniversityBody): AddUniversityModel

    @POST("templatefields/zones")
    suspend fun addArea(@Body areaBody: AreaBody): AddAreaModel

    @POST("templatefields/languages")
    suspend fun addLanguage(@Body languageBody: LanguageBody): AddLanguageModel
}