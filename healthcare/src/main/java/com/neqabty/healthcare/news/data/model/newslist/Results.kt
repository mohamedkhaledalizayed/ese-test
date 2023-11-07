package com.neqabty.healthcare.news.data.model.newslist


import androidx.annotation.Keep

@Keep
data class Results(
    val news: List<New>
)