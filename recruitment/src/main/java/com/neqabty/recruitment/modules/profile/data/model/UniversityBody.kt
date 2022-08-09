package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UniversityBody(
    @SerializedName("university")
    val university: University
)

@Keep
data class University(
    @SerializedName("name")
    val name: String
)