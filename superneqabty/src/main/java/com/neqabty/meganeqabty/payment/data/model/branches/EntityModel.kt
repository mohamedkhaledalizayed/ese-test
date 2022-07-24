package com.neqabty.meganeqabty.payment.data.model.branches


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
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("registration_notes")
    val registrationNotes: String,
    @SerializedName("requirements")
    val requirements: List<Any>,
    @SerializedName("type")
    val type: Type,
    @SerializedName("updated_at")
    val updatedAt: String
)