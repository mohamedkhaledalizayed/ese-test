package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SkillsBody(
    @SerializedName("skill")
    val skill: Skill
)

@Keep
data class Skill(
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)