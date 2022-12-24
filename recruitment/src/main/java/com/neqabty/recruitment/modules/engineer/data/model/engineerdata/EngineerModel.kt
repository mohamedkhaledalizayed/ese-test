package com.neqabty.recruitment.modules.engineer.data.model.engineerdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: CityModel,
    @SerializedName("courses")
    val courses: List<CourseModel>,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("department")
    val department: DepartmentModel,
    @SerializedName("educations")
    val educations: List<EducationModel>,
    @SerializedName("email")
    val email: String,
    @SerializedName("experiences")
    val experiences: List<ExperienceModel>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("governorate")
    val governorate: GovernorateModel,
    @SerializedName("grade")
    val grade: String?,
    @SerializedName("graduation_year")
    val graduationYear: Int,
    @SerializedName("languages")
    val languages: List<LanguageModel>,
    @SerializedName("linkedIn_link")
    val linkedInLink: String,
    @SerializedName("marital_status")
    val maritalStatus: MaritalStatusModel,
    @SerializedName("membership_id")
    val membershipId: String,
    @SerializedName("membership_id_file")
    val membershipIdFile: String?,
    @SerializedName("military_file")
    val militaryFile: String?,
    @SerializedName("military_status")
    val militaryStatus: MilitaryStatusModel,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("national_id_file")
    val nationalIdFile: String?,
    @SerializedName("nationality")
    val nationality: NationalityModel,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("skills")
    val skills: List<SkillModel>,
    @SerializedName("university")
    val university: UniversityModel,
    @SerializedName("working_status")
    val workingStatus: String,
    @SerializedName("years_of_experience")
    val yearsOfExperience: Int,
    @SerializedName("zone")
    val zone: ZoneModel
)