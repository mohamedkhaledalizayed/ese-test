package com.neqabty.healthcare.commen.clinido.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ClinidoBody(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("entity_code")
    val entityCode: String
)