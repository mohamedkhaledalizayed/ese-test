package com.neqabty.recruitment.modules.profile.data.model.universities


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UniversityModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)