package com.neqabty.recruitment.modules.news.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NewsModel(
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_file")
    val imageFile: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)