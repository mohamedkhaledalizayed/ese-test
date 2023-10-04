package com.neqabty.healthcare.syndicates.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SyndicateResponse(
    @SerializedName("entities")
    val syndicateModels: List<SyndicateModel> = listOf()
)