package com.neqabty.meganeqabty.payment.data.model.branches


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Data(
    @SerializedName("entity_branches")
    val entityBranches: List<EntityBranche>
)