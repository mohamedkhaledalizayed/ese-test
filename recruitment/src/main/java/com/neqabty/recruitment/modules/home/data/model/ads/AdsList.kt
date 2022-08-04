package com.neqabty.recruitment.modules.home.data.model.ads


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AdsList(
    @SerializedName("ads")
    val ads: List<AdModel>
)