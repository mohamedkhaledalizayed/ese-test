package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("links")
    val userLinks: UserLinks,
    @SerializedName("membership_id")
    val membershipId: Int,
    @SerializedName("std_category")
    val stdCategory: StudentCategory
)