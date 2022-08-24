package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerSkillsBody(
    @SerializedName("engineerskill")
    val engineerskill: Engineerskill
)