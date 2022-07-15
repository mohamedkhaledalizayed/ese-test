package com.neqabty.healthcare.modules.profile.data.model.profile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("followers")
    val followers: List<Follower>,
    @SerializedName("id")
    val id: String,
    @SerializedName("job")
    val job: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("personal_image")
    val personalImage: String,
    @SerializedName("qr_code")
    val qrCode: String,
    @SerializedName("subscribed_packages")
    val subscribedPackages: List<SubscribedPackage>,
    @SerializedName("syndicate_id")
    val syndicateId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("wallet")
    val wallet: Wallet
)