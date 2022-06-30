package com.neqabty.meganeqabty.profile.data.model.profile


import com.google.gson.annotations.SerializedName

data class EntityModel(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)