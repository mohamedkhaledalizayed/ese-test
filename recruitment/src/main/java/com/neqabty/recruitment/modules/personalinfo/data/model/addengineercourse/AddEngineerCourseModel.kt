package com.neqabty.recruitment.modules.personalinfo.data.model.addengineercourse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddEngineerCourseModel(
    @SerializedName("engineercourses")
    val engineercourses: List<Engineercourse>
)