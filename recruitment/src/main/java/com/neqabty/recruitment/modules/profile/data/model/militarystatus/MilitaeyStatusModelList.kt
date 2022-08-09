package com.neqabty.recruitment.modules.profile.data.model.militarystatus


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MilitaeyStatusModelList(
    @SerializedName("militarystatuses")
    val militarystatuses: List<MilitaryStatusModel>
)