package com.neqabty.healthcare.modules.search.data.model.packages


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Price(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("syndicate_name")
    val syndicateName: String
)