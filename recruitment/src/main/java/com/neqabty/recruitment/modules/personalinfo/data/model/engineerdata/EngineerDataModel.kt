package com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerDataModel(
    @SerializedName("engineers")
    val engineers: List<EngineerModel>
)