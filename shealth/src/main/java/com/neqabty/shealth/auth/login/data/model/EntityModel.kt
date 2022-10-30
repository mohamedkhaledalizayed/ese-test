package com.neqabty.shealth.auth.login.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

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