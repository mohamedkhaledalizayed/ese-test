package com.neqabty.recruitment.modules.profile.data.model.addengineerexperience


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerExperienceModel(
    @SerializedName("experiences")
    val experiences: List<Experience>
)