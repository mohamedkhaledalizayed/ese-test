package com.neqabty.recruitment.modules.profile.data.api

import com.neqabty.recruitment.modules.profile.data.model.*
import com.neqabty.recruitment.modules.profile.data.model.addarea.AddAreaModel
import com.neqabty.recruitment.modules.profile.data.model.addcourse.AddCourseModel
import com.neqabty.recruitment.modules.profile.data.model.addengineercourse.AddEngineerCourseModel
import com.neqabty.recruitment.modules.profile.data.model.addengineerexperience.EngineerExperienceModel
import com.neqabty.recruitment.modules.profile.data.model.addengineerlanguage.AddEngineerLanguageModel
import com.neqabty.recruitment.modules.profile.data.model.addengineerskills.AddEngineerSkillsModel
import com.neqabty.recruitment.modules.profile.data.model.addlanguage.AddLanguageModel
import com.neqabty.recruitment.modules.profile.data.model.addskills.AddSkillsModel
import com.neqabty.recruitment.modules.profile.data.model.adduniversity.AddUniversityModel
import com.neqabty.recruitment.modules.profile.data.model.area.AreaModelList
import com.neqabty.recruitment.modules.profile.data.model.companies.CompaniesModelList
import com.neqabty.recruitment.modules.profile.data.model.country.CountryModelList
import com.neqabty.recruitment.modules.profile.data.model.cources.CoursesModelList
import com.neqabty.recruitment.modules.profile.data.model.department.DepartmentModelList
import com.neqabty.recruitment.modules.profile.data.model.engineercourses.EngineerCoursesModel
import com.neqabty.recruitment.modules.profile.data.model.engineerdata.EngineerDataModel
import com.neqabty.recruitment.modules.profile.data.model.engineerdata.EngineerModel
import com.neqabty.recruitment.modules.profile.data.model.engineerexperiences.EngineerExperiencesModel
import com.neqabty.recruitment.modules.profile.data.model.engineerlanguages.EngineerLanguagesModel
import com.neqabty.recruitment.modules.profile.data.model.engineerskills.EngineerSkillsModel
import com.neqabty.recruitment.modules.profile.data.model.governement.GovernmentListModel
import com.neqabty.recruitment.modules.profile.data.model.grades.GradesModelList
import com.neqabty.recruitment.modules.profile.data.model.industries.IndustriesModelList
import com.neqabty.recruitment.modules.profile.data.model.language.LanguageModelList
import com.neqabty.recruitment.modules.profile.data.model.maritalstatus.MaritalStatusModelList
import com.neqabty.recruitment.modules.profile.data.model.militarystatus.MilitaeyStatusModelList
import com.neqabty.recruitment.modules.profile.data.model.nationalities.NationalitiesModelList
import com.neqabty.recruitment.modules.profile.data.model.skills.SkillsModelList
import com.neqabty.recruitment.modules.profile.data.model.universities.UniversityLisModel
import retrofit2.http.*

interface ProfileApi {

    //Engineer

    @GET("engineer/engineers")
    suspend fun getEngineerData(): EngineerDataModel

    @PATCH("engineer/engineers/{id}")
    suspend fun updateEngineerData(@Path("id") id: String, @Body engineerBody: EngineerBody): EngineerModel

    //Engineer Courses

    @GET("engineer/engineer_courses")
    suspend fun getEngineerCourses(@Query("filter{engineer}") engineer: Int): EngineerCoursesModel

    @POST("engineer/engineer_courses")
    suspend fun addEngineerCourse(@Body engineerCourseBody: EngineerCourseBody): AddEngineerCourseModel

    @DELETE("engineer/engineer_courses/{id}")
    suspend fun deleteEngineerCourse(@Path("id") id: String): String

    //Engineer Language

    @GET("engineer/engineer_languages")
    suspend fun getEngineerLanguages(@Query("filter{engineer}") id: String): EngineerLanguagesModel

    @POST("engineer/engineer_languages")
    suspend fun addEngineerLanguage(@Body engineerLanguageBody: EngineerLanguageBody): AddEngineerLanguageModel

    @DELETE("engineer/engineer_languages/{id}")
    suspend fun deleteEngineerLanguage(@Path("id") id: String): String

    //Engineer Experiences

    @GET("engineer/experience")
    suspend fun getEngineerExperience(@Query("filter{engineer}") id: String): EngineerExperiencesModel

    @POST("engineer/experience")
    suspend fun addEngineerExperience(@Body engineerExperienceBody: EngineerExperienceBody): EngineerExperienceModel

    @DELETE("engineer/experience/{id}")
    suspend fun deleteEngineerExperience(@Path("id") id: String): String

    //Engineer Skills

    @POST("engineer/engineer_skills")
    suspend fun getEngineerSkills(@Query("filter{engineer}") id: String): EngineerSkillsModel

    @POST("engineer/engineer_skills")
    suspend fun addEngineerSkills(@Body engineerSkillsBody: EngineerSkillsBody): AddEngineerSkillsModel

    @POST("engineer/engineer_skills/{id}")
    suspend fun deleteEngineerSkills(@Path("id") id: String): String




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