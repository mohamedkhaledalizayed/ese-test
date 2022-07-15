package com.neqabty.healthcare.modules.suggestions.data.model.complaints


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("complaint_number")
    val complaintNumber: Int
)