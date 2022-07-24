package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class CourseInOffer(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("lab")
    val lab: Lab = Lab(),
    @SerializedName("num_of_sessions")
    val numOfSessions: Int = 0,
    @SerializedName("prerequisites")
    val prerequisites: String = "",
    @SerializedName("syllabus")
    val syllabus: String = "",
    @SerializedName("title")
    val title: String = ""
)