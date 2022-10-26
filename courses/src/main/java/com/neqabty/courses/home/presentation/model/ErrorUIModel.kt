package com.neqabty.courses.home.presentation.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ErrorUIModel(
    @SerializedName("detail")
    val detail: String
)