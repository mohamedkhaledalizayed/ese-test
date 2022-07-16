package com.neqabty.healthcare.modules.subscribtions.data.model.relationstypes


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("relations")
    val relations: List<Relation>
)