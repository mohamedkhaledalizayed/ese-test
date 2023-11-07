package com.neqabty.healthcare.news.data.model.newslist


import androidx.annotation.Keep

@Keep
data class NewsListModel(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: Results
)