package com.neqabty.meganeqabty.profile.data.model.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class EntityBody(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)