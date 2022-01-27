package com.neqabty.news.modules.home.data.model


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("news")
    val news: List<News> = listOf()
)