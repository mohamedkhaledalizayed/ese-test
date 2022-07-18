package com.neqabty.healthcare.modules.search.data.model.packages


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class PackageModel(
    @SerializedName("description")
    val description: String,
    @SerializedName("insurance_amount")
    val insuranceAmount: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nedded_info")
    val neddedInfo: String,
    @SerializedName("prices")
    val prices: List<Price>,
    @SerializedName("target_groups")
    val targetGroups: String
)