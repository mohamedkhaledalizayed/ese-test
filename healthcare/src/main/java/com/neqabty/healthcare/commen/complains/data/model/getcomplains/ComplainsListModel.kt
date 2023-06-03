package com.neqabty.healthcare.commen.complains.data.model.getcomplains


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ComplainsListModel(
    @SerializedName("data")
    val `data`: List<ComplainModel>
)