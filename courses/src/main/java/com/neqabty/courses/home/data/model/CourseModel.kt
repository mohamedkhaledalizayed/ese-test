package com.neqabty.courses.home.data.model


import com.google.gson.annotations.SerializedName

data class CourseModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?=null,
    @SerializedName("num_of_sessions")
    val numOfSessions: Int,
    @SerializedName("prerequisites")
    val prerequisites: Any? = null,
    @SerializedName("syllabus")
    val syllabus: String,
    @SerializedName("title")
    val title: String
)