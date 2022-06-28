package com.neqabty.meganeqabty.home.data.model


import com.google.gson.annotations.SerializedName

data class Ad(
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("entity")
    val entity: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("news_id")
    val newsId: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("url")
    val url: String? = ""
)