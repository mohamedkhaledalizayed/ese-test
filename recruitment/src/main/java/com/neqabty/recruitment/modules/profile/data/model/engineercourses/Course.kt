package com.neqabty.recruitment.modules.profile.data.model.engineercourses


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Course(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)