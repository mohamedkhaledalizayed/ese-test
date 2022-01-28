package com.neqabty.login.modules.home.data.model


import com.google.gson.annotations.SerializedName

data class Service(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("domain")
    val domain: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("links")
    val links: LinksX = LinksX(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("require_registration")
    val requireRegistration: Boolean = false,
    @SerializedName("updated_at")
    val updatedAt: String = ""
)