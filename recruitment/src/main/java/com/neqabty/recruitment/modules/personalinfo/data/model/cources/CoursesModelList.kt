package com.neqabty.recruitment.modules.personalinfo.data.model.cources


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CoursesModelList(
    @SerializedName("courses")
    val courses: List<CourseModel>
)