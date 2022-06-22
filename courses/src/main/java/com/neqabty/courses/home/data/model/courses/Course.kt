package com.neqabty.courses.home.data.model.courses


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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