package com.neqabty.healthcare.commen.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UpdatePasswordBody(
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("old_password")
    val oldPassword: String
)