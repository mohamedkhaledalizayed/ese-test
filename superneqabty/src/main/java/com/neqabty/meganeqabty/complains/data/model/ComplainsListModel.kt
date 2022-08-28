package com.neqabty.meganeqabty.complains.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ComplainsListModel(
    @SerializedName("data")
    val `data`: List<ComplainModel>
)