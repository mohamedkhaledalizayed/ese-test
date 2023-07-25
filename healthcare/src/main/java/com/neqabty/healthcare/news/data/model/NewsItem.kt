package com.neqabty.healthcare.news.data.model


import androidx.annotation.Keep

@Keep
data class NewsItem(
    val author: Author,
    val content: String,
    val headline: String,
    val id: Int,
    val image: String?,
    val source: String?,
    val type: String?,
    val url: String?
)