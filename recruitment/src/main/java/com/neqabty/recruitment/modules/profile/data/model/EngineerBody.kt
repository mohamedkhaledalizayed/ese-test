package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerBody(
    @SerializedName("address")
    val address: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("graduation_year")
    val graduationYear: Int,
    @SerializedName("linkedIn_link")
    val linkedInLink: String,
    @SerializedName("marital_status")
    val maritalStatus: Int,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("working_status")
    val workingStatus: String
)