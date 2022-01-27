package com.neqabty.login.modules.home.data.model


import com.google.gson.annotations.SerializedName

data class SyndicateModel(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("links")
    val links: Links = Links(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("registration_notes")
    val registrationNotes: String? = "",
    @SerializedName("requirements")
    val requirements: List<Requirement> = listOf(),
    @SerializedName("services")
    val services: List<Service> = listOf(),
    @SerializedName("type")
    val type: Type = Type(),
    @SerializedName("updated_at")
    val updatedAt: String = ""
)