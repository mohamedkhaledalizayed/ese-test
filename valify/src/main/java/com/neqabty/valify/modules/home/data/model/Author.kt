package com.neqabty.valify.modules.home.data.model


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("entity_code")
    val entityCode: String,
    @SerializedName("id")
    val id: Int
)