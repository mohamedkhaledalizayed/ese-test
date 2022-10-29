package com.neqabty.courses.offers.data.model.reschedulerequest


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class RescheduleRequestModel(
    @SerializedName("course_offer_reschedule")
    val courseOfferReschedule: CourseOfferReschedule
)