package com.neqabty.recruitment.modules.personalinfo.data.model.engineercourses


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerCoursesModel(
    @SerializedName("engineercourses")
    val engineercourses: List<Engineercourse>
)