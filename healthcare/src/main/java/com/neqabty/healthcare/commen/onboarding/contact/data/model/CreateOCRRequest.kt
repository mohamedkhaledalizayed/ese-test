package com.neqabty.healthcare.commen.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

@Keep
data class CreateOCRRequest(
    @SerializedName("ir_face")
    val idFace: MultipartBody.Part?,
    @SerializedName("id_back")
    val idBack: MultipartBody.Part?,
    @SerializedName("nationalId")
    val nationalId: String,
    @SerializedName("mobile")
    val mobile: String
)