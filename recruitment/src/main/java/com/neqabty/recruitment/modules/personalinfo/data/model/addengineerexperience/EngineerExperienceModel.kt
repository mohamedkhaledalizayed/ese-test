package com.neqabty.recruitment.modules.personalinfo.data.model.addengineerexperience


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerExperienceModel(
    @SerializedName("experiences")
    val experiences: List<Experience>
)