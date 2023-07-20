package com.neqabty.healthcare.profile.data.model.updatepaswword


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdatePasswordModel(
    @SerializedName("message")
    val message: String
)