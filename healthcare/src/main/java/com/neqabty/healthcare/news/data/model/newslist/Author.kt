package com.neqabty.healthcare.news.data.model.newslist


import androidx.annotation.Keep

@Keep
data class Author(
    val code: String,
    val created_at: String,
    val id: Int,
    val image: String?,
    val mobile: String?,
    val name: String,
    val syndicate_status: Boolean,
    val type: Type,
    val updated_at: String
)