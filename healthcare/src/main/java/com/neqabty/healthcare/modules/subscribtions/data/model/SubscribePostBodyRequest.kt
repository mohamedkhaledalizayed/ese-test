package com.neqabty.healthcare.modules.subscribtions.data.model


import com.google.gson.annotations.SerializedName

data class SubscribePostBodyRequest(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("back_id_image")
    val backIdImage: String = "",
    @SerializedName("birth_date")
    val birthDate: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("front_id_image")
    val frontIdImage: String = "",
    @SerializedName("full_name")
    val fullName: String = "",
    @SerializedName("job")
    val job: String = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("national_id")
    val nationalId: String = "",
    @SerializedName("package_id")
    val packageId: String = "",
    @SerializedName("personal_image")
    val personalImage: String = "",
    @SerializedName("referral_number")
    val referralNumber: String = "",
    @SerializedName("syndicate_id")
    val syndicateId: Int = 0
)