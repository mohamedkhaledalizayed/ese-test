package com.neqabty.healthcare.modules.profile.data.model.profile


import com.google.gson.annotations.SerializedName

data class Follower(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("laravel_through_key")
    val laravelThroughKey: String,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("qr_code")
    val qrCode: String,
    @SerializedName("relation_type")
    val relationType: Int,
    @SerializedName("subscriber_id")
    val subscriberId: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_number")
    val userNumber: Any
)