package com.neqabty.login.modules.login.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class EntityModel(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)