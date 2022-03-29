package com.neqabty.superneqabty.paymentdetails.data.model.inquiryresponse


import com.google.gson.annotations.SerializedName

data class Service(
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("domain")
    val domain: String,
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("links")
    val links: Links,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("require_registration")
    val requireRegistration: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String
)