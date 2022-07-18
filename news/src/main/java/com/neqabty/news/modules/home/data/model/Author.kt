package com.neqabty.news.modules.home.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Author(
    @SerializedName("entity_code")
    val entityCode: String,
    @SerializedName("id")
    val id: Int
)