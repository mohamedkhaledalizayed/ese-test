package com.neqabty.signup.modules.signup.data.model.syndicates


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SyndicateListModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)