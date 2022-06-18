package com.neqabty.healthcare.modules.search.data.model.search


import com.google.gson.annotations.SerializedName

data class Degree(
    @SerializedName("degree_name")
    val degreeName: String,
    @SerializedName("id")
    val id: Int
)