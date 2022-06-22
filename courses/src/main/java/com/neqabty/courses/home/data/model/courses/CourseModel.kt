package com.neqabty.courses.home.data.model.courses


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CourseModel(
    @SerializedName("courses")
    val courses: List<Course>
)