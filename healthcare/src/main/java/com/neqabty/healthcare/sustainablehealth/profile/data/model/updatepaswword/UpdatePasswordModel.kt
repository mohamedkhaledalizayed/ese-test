package com.neqabty.healthcare.sustainablehealth.profile.data.model.updatepaswword


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UpdatePasswordModel(
    @SerializedName("message")
    val message: String
)