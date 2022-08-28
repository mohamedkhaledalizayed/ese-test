package com.neqabty.recruitment.modules.profile.data.model.engineercourses


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerCoursesModel(
    @SerializedName("engineercourses")
    val engineercourses: List<Engineercourse>
)