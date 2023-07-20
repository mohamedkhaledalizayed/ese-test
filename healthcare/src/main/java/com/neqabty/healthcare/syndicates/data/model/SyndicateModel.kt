package com.neqabty.healthcare.syndicates.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
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
    @SerializedName("entity_validations")
    val entityValidations: List<EntityValidation> = listOf(),
    @SerializedName("services")
    val services: List<Service> = listOf(),
    @SerializedName("type")
    val type: Type = Type(),
    @SerializedName("updated_at")
    val updatedAt: String = ""
)

data class EntityValidation(
    @SerializedName("validation_name")
    val validationName: String,
    @SerializedName("value")
    val value: Boolean
)