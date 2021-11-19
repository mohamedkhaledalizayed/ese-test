package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Pricing(
    @SerializedName("id")
    val id: Int,
    @SerializedName("offer")
    val offer: Int?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("student_category")
    val studentCategory: StudentCategory
)