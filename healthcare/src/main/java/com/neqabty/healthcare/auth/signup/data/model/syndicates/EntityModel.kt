package com.neqabty.healthcare.auth.signup.data.model.syndicates


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class EntityModel(
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("links")
    val links: Links,
    @SerializedName("name")
    val name: String,
    @SerializedName("registration_notes")
    val registrationNotes: String,
    @SerializedName("requirements")
    val requirements: List<Requirement>,
    @SerializedName("type")
    val type: Type,
    @SerializedName("updated_at")
    val updatedAt: String
)