package com.neqabty.signup.modules.servicessyndicate.data.model.signup


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

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