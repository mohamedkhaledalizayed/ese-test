package com.neqabty.recruitment.modules.personalinfo.data.model.governement


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GovernmentListModel(
    @SerializedName("governorates")
    val governorates: List<GovernorateModel>
)