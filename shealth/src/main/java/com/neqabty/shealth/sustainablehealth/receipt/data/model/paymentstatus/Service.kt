package com.neqabty.shealth.sustainablehealth.receipt.data.model.paymentstatus


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Service(
    @SerializedName("actions")
    val actions: List<String>,
    @SerializedName("code")
    val code: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("links")
    val links: Links,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("service_category")
    val serviceCategory: Any,
    @SerializedName("type")
    val type: String
)