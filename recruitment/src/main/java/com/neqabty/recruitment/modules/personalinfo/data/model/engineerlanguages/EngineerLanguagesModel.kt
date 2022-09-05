package com.neqabty.recruitment.modules.personalinfo.data.model.engineerlanguages


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerLanguagesModel(
    @SerializedName("engineerlanguages")
    val engineerlanguages: List<Engineerlanguage>
)