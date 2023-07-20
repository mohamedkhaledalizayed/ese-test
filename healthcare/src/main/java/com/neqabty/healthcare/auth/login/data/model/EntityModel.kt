package com.neqabty.healthcare.auth.login.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EntityModel(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)