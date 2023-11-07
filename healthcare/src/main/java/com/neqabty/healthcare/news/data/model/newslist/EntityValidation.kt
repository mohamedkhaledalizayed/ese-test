package com.neqabty.healthcare.news.data.model.newslist


import androidx.annotation.Keep

@Keep
data class EntityValidation(
    val validation_name: String,
    val value: Boolean
)