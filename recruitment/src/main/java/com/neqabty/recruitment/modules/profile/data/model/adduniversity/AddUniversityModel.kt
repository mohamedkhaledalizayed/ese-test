package com.neqabty.recruitment.modules.profile.data.model.adduniversity


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddUniversityModel(
    @SerializedName("university")
    val university: University
)