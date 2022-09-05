package com.neqabty.recruitment.modules.personalinfo.data.model.engineerskills


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerSkillsModel(
    @SerializedName("experiences")
    val experiences: List<Experience>
)