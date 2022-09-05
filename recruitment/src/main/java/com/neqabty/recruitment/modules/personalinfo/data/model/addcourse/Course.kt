package com.neqabty.recruitment.modules.personalinfo.data.model.addcourse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Course(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)