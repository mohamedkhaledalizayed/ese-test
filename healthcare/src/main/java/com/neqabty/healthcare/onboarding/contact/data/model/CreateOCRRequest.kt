package com.neqabty.healthcare.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CreateOCRRequest(
//    @SerializedName("id_face")
//    val idFace: MultipartBody.Part?,
//    @SerializedName("id_back")
//    val idBack: MultipartBody.Part?,
    @SerializedName("nationalId")
    val nationalId: String,
    @SerializedName("mobile")
    val mobile: String
)