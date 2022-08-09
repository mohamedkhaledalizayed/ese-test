package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CourseBody(
    @SerializedName("course")
    val course: Course
)

@Keep
data class Course(
    @SerializedName("name")
    val name: String
)