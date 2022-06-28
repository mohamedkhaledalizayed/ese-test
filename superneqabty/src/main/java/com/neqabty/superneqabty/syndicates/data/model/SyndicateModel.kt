package com.neqabty.superneqabty.syndicates.data.model


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
    @SerializedName("name")
    val name: String = "",
    @SerializedName("registration_notes")
    val registrationNotes: String? = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("type")
    val type: Int = 0
)