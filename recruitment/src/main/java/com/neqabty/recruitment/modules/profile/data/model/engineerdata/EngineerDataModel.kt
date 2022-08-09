package com.neqabty.recruitment.modules.profile.data.model.engineerdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerDataModel(
    @SerializedName("engineers")
    val engineers: List<EngineerModel>
)