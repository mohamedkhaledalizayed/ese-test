package com.neqabty.healthcare.commen.profile.data.model.membershipcardstatus


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MemberShipCardStatus(
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
    @SerializedName("unique_id")
    val uniqueId: String,
    @SerializedName("updated_at")
    val updatedAt: String
)