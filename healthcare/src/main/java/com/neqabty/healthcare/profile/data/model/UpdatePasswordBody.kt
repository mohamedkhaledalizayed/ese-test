package com.neqabty.healthcare.profile.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdatePasswordBody(
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("old_password")
    val oldPassword: String
)