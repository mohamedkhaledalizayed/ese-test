package com.neqabty.recruitment.modules.profile.data.model.addcourse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddCourseModel(
    @SerializedName("course")
    val course: Course
)