package com.neqabty.healthcare.sustainablehealth.subscribtions.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UpdatePackageBody(
    @SerializedName("address")
    val address: String,
    @SerializedName("back_id_image")
    val backIdImage: String,
    @SerializedName("birth_date")
    val birthDate: String,
    @SerializedName("delivery_phone")
    val deliveryPhone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("front_id_image")
    val frontIdImage: String,
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
    @SerializedName("user_number")
    val userNumber: String
)