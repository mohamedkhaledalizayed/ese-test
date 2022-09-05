package com.neqabty.recruitment.modules.personalinfo.data.model.cources


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CourseModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)