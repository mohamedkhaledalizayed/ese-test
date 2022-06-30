package com.neqabty.meganeqabty.profile.data.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("account")
    val account: AccountModel,
    @SerializedName("blocked")
    val blocked: Any,
    @SerializedName("entity")
    val entity: EntityModel,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_fee_year")
    val lastFeeYear: Int,
    @SerializedName("license_end_date")
    val licenseEndDate: String,
    @SerializedName("membership_id")
    val membershipId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("national_id")
    val nationalId: Long
)