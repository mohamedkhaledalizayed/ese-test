package com.neqabty.news.modules.home.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsEntity(
    val author: Int = 0,
    val content: String = "",
    val createdAt: String = "",
    val headline: String = "",
    val id: Int = 0,
    val image: String = "",
    val source: String = "",
    val type: String = "",
    val updatedAt: String = "",
    val url: String? = ""
): Parcelable