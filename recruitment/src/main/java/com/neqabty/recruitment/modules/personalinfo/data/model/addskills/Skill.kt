package com.neqabty.recruitment.modules.personalinfo.data.model.addskills


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Skill(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)