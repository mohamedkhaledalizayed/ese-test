package com.neqabty.healthcare.mega.home.data.model.ads


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class AdsResponse(
    @SerializedName("ads")
    val ads: List<Ad> = listOf()
)