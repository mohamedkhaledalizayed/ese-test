package com.neqabty.superneqabty.home.data.model


import com.google.gson.annotations.SerializedName

data class AdsResponse(
    @SerializedName("ads")
    val ads: List<Ad> = listOf()
)