package com.neqabty.recruitment.modules.profile.data.model.language


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LanguageModelList(
    @SerializedName("languages")
    val languages: List<LanguageModel>
)