package com.neqabty.meganeqabty.payment.data.model.inquiryresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MethodsObj(
    @SerializedName("branch_method_id")
    val branchMethodId: Int,
    @SerializedName("branch_method_price")
    val branchMethodPrice: String,
    @SerializedName("home_method_id")
    val homeMethodId: Int,
    @SerializedName("home_method_price")
    val homeMethodPrice: String
)