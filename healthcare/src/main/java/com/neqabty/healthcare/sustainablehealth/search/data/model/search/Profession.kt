package com.neqabty.healthcare.sustainablehealth.search.data.model.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Profession(
    @SerializedName("id")
    val id: Int,
    @SerializedName("profession_name")
    val professionName: String?
)