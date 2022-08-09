package com.neqabty.recruitment.modules.profile.data.model.skills


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SkillsModelList(
    @SerializedName("skills")
    val skills: List<SkillModel>
)