package com.neqabty.healthcare.syndicateservices.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SyndicateService(
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("service_category")
    val serviceCategory: ServiceCategory?,
    @SerializedName("type")
    val type: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("actions")
    val actions: List<String>,
    @SerializedName("links")
    val links: Links
)

data class ServiceCategory(
    @SerializedName("name")
    val name: String
)

data class Links(
    @SerializedName("entity")
    val entity: String
)
