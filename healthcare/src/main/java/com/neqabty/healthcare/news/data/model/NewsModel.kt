package com.neqabty.healthcare.news.data.model


import androidx.annotation.Keep

@Keep
data class NewsModel(
    val news: List<NewsItem>
)