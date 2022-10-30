package com.neqabty.shealth.news.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NewsResponse(
    @SerializedName("news")
    val news: List<News> = listOf()
)