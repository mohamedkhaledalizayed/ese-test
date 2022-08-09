package com.neqabty.recruitment.modules.profile.data.model.skills


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SkillModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)