package com.neqabty.healthcare.auth.signup.data.model.syndicatemember


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Entity(
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