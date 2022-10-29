package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CourseOfferReschedule(
    @SerializedName("note")
    val note: String,
    @SerializedName("offer")
    val offer: Int,
    @SerializedName("student_mobile")
    val studentMobile: String
)