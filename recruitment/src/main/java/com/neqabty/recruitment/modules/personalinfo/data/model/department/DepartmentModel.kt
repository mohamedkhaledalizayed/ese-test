package com.neqabty.recruitment.modules.personalinfo.data.model.department


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DepartmentModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)