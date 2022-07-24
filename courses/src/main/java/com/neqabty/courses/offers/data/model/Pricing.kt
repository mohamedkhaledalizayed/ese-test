package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Pricing(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("offer")
    val offer: Int = 0,
    @SerializedName("price")
    val price: String = "",
    @SerializedName("service_action_code")
    val serviceActionCode: String = "",
    @SerializedName("service_code")
    val serviceCode: String = "",
    @SerializedName("student_category")
    val studentCategory: StudentCategory = StudentCategory()
)