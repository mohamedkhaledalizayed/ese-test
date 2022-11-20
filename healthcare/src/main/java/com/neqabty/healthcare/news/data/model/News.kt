package com.neqabty.healthcare.news.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class News(
    @SerializedName("author")
    val author: Author ,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("headline")
    val headline: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("source")
    val source: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("url")
    val url: String? = ""
)