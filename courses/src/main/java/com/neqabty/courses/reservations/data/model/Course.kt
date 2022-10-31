package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Course(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("lab")
    val lab: Lab,
    @SerializedName("num_of_sessions")
    val numOfSessions: Int,
    @SerializedName("prerequisites")
    val prerequisites: String,
    @SerializedName("syllabus")
    val syllabus: String,
    @SerializedName("title")
    val title: String
)