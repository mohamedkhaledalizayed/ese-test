package com.neqabty.meganeqabty.profile.data.model.cardrequest


import com.google.gson.annotations.SerializedName

data class CardRequestModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("card_year")
    val cardYear: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("member")
    val member: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)