package com.neqabty.healthcare.payment.data.model.servicesaction


import com.google.gson.annotations.SerializedName

data class Service(
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: Any,
    @SerializedName("domain")
    val domain: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("links")
    val links: Links,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("require_cysr")
    val requireCysr: Boolean,
    @SerializedName("require_registration")
    val requireRegistration: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String
)