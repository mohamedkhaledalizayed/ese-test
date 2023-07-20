package com.neqabty.healthcare.complains.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ComplainModel(
    @SerializedName("account")
    val account: Int,
    @SerializedName("answer")
    val answer: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("entity")
    val entity: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("updated_at")
    val updatedAt: String
)