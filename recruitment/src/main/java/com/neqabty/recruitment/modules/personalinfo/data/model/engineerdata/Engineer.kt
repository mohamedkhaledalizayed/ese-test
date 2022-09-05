package com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Engineer(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: Int,
    @SerializedName("courses")
    val courses: List<Int>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("department")
    val department: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("governorate")
    val governorate: Int,
    @SerializedName("grade")
    val grade: Any,
    @SerializedName("graduation_year")
    val graduationYear: Int,
    @SerializedName("languages")
    val languages: List<Int>,
    @SerializedName("linkedIn_link")
    val linkedInLink: String,
    @SerializedName("marital_status")
    val maritalStatus: Int,
    @SerializedName("membership_id")
    val membershipId: String,
    @SerializedName("membership_id_file")
    val membershipIdFile: Any,
    @SerializedName("military_file")
    val militaryFile: String,
    @SerializedName("military_status")
    val militaryStatus: Int,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("national_id_file")
    val nationalIdFile: Any,
    @SerializedName("nationality")
    val nationality: Int,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("skills")
    val skills: List<Int>,
    @SerializedName("university")
    val university: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("working_status")
    val workingStatus: String,
    @SerializedName("years_of_experience")
    val yearsOfExperience: Int,
    @SerializedName("zone")
    val zone: Int
)