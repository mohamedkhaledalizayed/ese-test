package com.neqabty.recruitment.modules.personalinfo.data.model.department


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DepartmentModelList(
    @SerializedName("departments")
    val departments: List<DepartmentModel>
)