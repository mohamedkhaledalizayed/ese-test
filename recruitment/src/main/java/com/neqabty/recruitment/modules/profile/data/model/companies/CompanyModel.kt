package com.neqabty.recruitment.modules.profile.data.model.companies


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CompanyModel(
    @SerializedName("about")
    val about: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("established")
    val established: Int,
    @SerializedName("fax_number")
    val faxNumber: String,
    @SerializedName("headquarters")
    val headquarters: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("industry")
    val industry: List<Any>,
    @SerializedName("linkedIn_link")
    val linkedInLink: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("number_of_employees")
    val numberOfEmployees: Int,
    @SerializedName("owner_name")
    val ownerName: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("website")
    val website: String
)