package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("department")
    val department: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("graduate_year")
    val graduateYear: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("identity_card")
    val identityCard: String? = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("nequabty_id")
    val nequabtyId: String? = "",
    @SerializedName("std_category")
    val stdCategory: String = "",
    @SerializedName("student_status")
    val studentStatus: String = "",
    @SerializedName("university")
    val university: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)