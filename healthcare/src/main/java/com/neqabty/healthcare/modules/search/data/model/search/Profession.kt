package com.neqabty.healthcare.modules.search.data.model.search


import com.google.gson.annotations.SerializedName

data class Profession(
    @SerializedName("id")
    val id: Int,
    @SerializedName("profession_name")
    val professionName: String
)