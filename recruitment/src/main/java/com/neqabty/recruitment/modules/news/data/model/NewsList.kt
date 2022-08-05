package com.neqabty.recruitment.modules.news.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NewsList(
    @SerializedName("news")
    val news: List<NewsModel>
)