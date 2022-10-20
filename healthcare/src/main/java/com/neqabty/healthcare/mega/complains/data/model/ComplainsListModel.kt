package com.neqabty.healthcare.mega.complains.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ComplainsListModel(
    @SerializedName("data")
    val `data`: List<ComplainModel>
)