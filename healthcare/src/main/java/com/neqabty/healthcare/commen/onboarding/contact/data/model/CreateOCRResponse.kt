package com.neqabty.healthcare.commen.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CreateOCRResponse(
    @SerializedName("ocrs")
    val ocrs: List<OCRItem>
)

@Keep
data class OCRItem(
    @SerializedName("id_face")
    val idFace: String?,
    @SerializedName("id_back")
    val idBack: String?
)