package com.neqabty.healthcare.suggestions.data.model.complaints


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Data(
    @SerializedName("complaint_number")
    val complaintNumber: Int
)