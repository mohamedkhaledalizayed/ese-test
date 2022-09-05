package com.neqabty.recruitment.modules.personalinfo.data.model.addengineerskills


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Engineerskill(
    @SerializedName("engineer")
    val engineer: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("skill")
    val skill: Int
)