package com.neqabty.login.modules.home.data.model


import com.google.gson.annotations.SerializedName

data class SyndicateResponse(
    @SerializedName("entities")
    val syndicateModels: List<SyndicateModel> = listOf()
)