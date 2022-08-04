package com.neqabty.recruitment.modules.home.data.model.news


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NewsList(
    @SerializedName("news")
    val news: List<NewsModel>
)