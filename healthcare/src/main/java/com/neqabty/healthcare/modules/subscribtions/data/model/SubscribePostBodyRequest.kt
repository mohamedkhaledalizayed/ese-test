package com.neqabty.healthcare.modules.subscribtions.data.model


import android.net.Uri
import com.google.gson.annotations.SerializedName

data class SubscribePostBodyRequest(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("birth_date")
    val birthDate: String = "",
    @SerializedName("address")
    val address: String = "",
    @SerializedName("job")
    val job: String = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("national_id")
    val nationalId: String = "",
    @SerializedName("entity_code")
    val entityCode: String = "",
    @SerializedName("service_action_code")
    val serviceActionCode: String = "",
    @SerializedName("referral_number")
    val referralNumber: String? = "",
    @SerializedName("personal_image")
    val personalImage: String = "",
    @SerializedName("front_id_image")
    val frontIdImage: String = "",
    @SerializedName("back_id_image")
    val backIdImage: String = "",
    @SerializedName("followers")
    val followers: List<Followers> = mutableListOf()
)

data class Followers(
    @SerializedName("name")
    val name: String = "",
    val relation: String = "",
    val imageUri: Uri,
    @SerializedName("national_id")
    val national_id: String = "",
    @SerializedName("relation_type")
    val relation_type: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("front_id_image")
    val frontIdImage: String = "",
    @SerializedName("back_id_image")
    val backIdImage: String = ""
)