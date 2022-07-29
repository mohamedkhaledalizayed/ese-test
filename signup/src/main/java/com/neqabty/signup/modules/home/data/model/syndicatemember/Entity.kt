package com.neqabty.signup.modules.home.data.model.syndicatemember


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Entity(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)