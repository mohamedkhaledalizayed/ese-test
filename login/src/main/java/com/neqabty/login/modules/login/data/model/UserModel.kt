package com.neqabty.login.modules.login.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class UserModel(
    @SerializedName("account")
    val account: Account,
    @SerializedName("entity")
    val entity: Entity,
    @SerializedName("name")
    val name: String,
    @SerializedName("membership_id")
    val membershipId: String,
    @SerializedName("last_fee_year")
    val last_fee_year: String,
    @SerializedName("national_id")
    val national_id: String,
    @SerializedName("license_end_date")
    val license_end_date: String,
    @SerializedName("blocked")
    val blocked: Boolean?
)