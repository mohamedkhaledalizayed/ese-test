package com.neqabty.healthcare.relation.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Data(
    @SerializedName("relations")
    val relations: List<RelationModel>
)