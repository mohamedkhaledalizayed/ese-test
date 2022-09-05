package com.neqabty.recruitment.modules.personalinfo.data.model.grades


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GradesModelList(
    @SerializedName("grades")
    val grades: List<GradeModel>
)