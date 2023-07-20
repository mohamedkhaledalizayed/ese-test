package com.neqabty.healthcare.profile.data.model.cardrequest


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class CardRequestModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("card_year")
    val cardYear: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("delivered")
    val delivered: String,
    @SerializedName("delivery_time")
    val deliveryTime: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("member")
    val member: Member,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String,
    @SerializedName("updated_at")
    val updatedAt: String
)