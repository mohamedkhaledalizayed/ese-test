package com.neqabty.recruitment.modules.profile.data.model.engineerexperiences


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerExperiencesModel(
    @SerializedName("experiences")
    val experiences: List<Experience>
)