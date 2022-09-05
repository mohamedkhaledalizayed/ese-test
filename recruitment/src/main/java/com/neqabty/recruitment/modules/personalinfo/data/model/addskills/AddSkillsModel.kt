package com.neqabty.recruitment.modules.personalinfo.data.model.addskills


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddSkillsModel(
    @SerializedName("skill")
    val skill: Skill
)