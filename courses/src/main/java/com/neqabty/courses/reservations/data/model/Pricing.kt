package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Pricing(
    @SerializedName("id")
    val id: Int,
    @SerializedName("offer")
    val offer: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("service_action_code")
    val serviceActionCode: String,
    @SerializedName("service_code")
    val serviceCode: String,
    @SerializedName("student_category")
    val studentCategory: StudentCategory
)