package com.neqabty.recruitment.modules.news.data.model.newsdetails


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.neqabty.recruitment.modules.news.data.model.NewsModel

@Keep
data class NewsDetailsModel(
    @SerializedName("news")
    val news: NewsModel
)