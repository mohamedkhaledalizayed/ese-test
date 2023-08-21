package com.neqabty.healthcare.payment.data.model.branches


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class BranchesListModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)