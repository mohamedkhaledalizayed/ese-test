package com.neqabty.healthcare.modules.profile.data.model.relationstypes


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Data(
    @SerializedName("relations")
    val relations: List<RelationModel>
)