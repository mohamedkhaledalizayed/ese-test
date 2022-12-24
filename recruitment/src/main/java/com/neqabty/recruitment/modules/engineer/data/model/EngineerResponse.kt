package com.neqabty.recruitment.modules.engineer.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.neqabty.recruitment.modules.engineer.data.model.engineerdata.EngineerModel

@Keep
data class EngineerResponse(
    @SerializedName("engineer")
    val engineer: EngineerModel
)