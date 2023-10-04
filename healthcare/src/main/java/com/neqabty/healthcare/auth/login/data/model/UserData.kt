package com.neqabty.healthcare.auth.login.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserData(
    @SerializedName("account")
    val account: Account,
    @SerializedName("address")
    val address: Any,
    @SerializedName("amount_due")
    val amountDue: String,
    @SerializedName("birth_date")
    val birthDate: Any,
    @SerializedName("blocked")
    val blocked: Any,
    @SerializedName("division")
    val division: Any,
    @SerializedName("employer")
    val employer: Any,
    @SerializedName("entity")
    val entityModel: EntityModel,
    @SerializedName("entity_branch")
    val entityBranch: Any,
    @SerializedName("graduation_year")
    val graduationYear: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_fee_year")
    val lastFeeYear: Int,
    @SerializedName("license_end_date")
    val licenseEndDate: String,
    @SerializedName("membership_id")
    val membershipId: Int,
    @SerializedName("name")
    val name: Any,
    @SerializedName("national_id")
    val nationalId: String?,
    @SerializedName("registration_date")
    val registrationDate: Any,
    @SerializedName("serial_number")
    val serialNumber: Any
)