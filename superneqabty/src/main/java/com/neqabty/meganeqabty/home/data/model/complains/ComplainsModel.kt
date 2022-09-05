package com.neqabty.meganeqabty.home.data.model.complains


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ComplainsModel(
    @SerializedName("account")
    val account: Any,
    @SerializedName("answer")
    val answer: String,
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
    @SerializedName("receipt_id")
    val receiptId: String,
    @SerializedName("updated_at")
    val updatedAt: String
)