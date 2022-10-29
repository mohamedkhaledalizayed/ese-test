package com.neqabty.courses.offers.data.model.reschedulerequest


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CourseOfferReschedule(
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("note")
    val note: String,
    @SerializedName("offer")
    val offer: Int,
    @SerializedName("student")
    val student: Int
)