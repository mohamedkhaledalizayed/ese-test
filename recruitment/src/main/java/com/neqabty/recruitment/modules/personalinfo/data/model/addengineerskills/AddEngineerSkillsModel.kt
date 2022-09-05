package com.neqabty.recruitment.modules.personalinfo.data.model.addengineerskills


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddEngineerSkillsModel(
    @SerializedName("engineerskill")
    val engineerskill: Engineerskill
)