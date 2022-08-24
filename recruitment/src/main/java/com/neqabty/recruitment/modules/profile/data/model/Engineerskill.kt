package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Engineerskill(
    @SerializedName("engineer")
    val engineer: Int,
    @SerializedName("skill")
    val skill: Int
)