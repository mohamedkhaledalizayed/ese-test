package com.neqabty.healthcare.complains.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ComplainsListModel(
    @SerializedName("data")
    val `data`: List<ComplainModel>
)