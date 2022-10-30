package com.neqabty.shealth.sustainablehealth.mypackages.data.model.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Client(
    @SerializedName("address")
    val address: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
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
    @SerializedName("birth_date")
    val birthDate: String,
    @SerializedName("qr_code")
    val qrCode: String,
    @SerializedName("subscribed_package")
    val subscribedPackage: Boolean,
    @SerializedName("syndicate_id")
    val syndicateId: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)