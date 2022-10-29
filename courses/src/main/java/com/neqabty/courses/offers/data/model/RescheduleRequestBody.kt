package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class RescheduleRequestBody(
    @SerializedName("course_offer_reschedule")
    val courseOfferReschedule: CourseOfferReschedule
)