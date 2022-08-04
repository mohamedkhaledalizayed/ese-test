package com.neqabty.recruitment.modules.home.data.model.ads


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AdModel(
    @SerializedName("company")
    val company: CompanyModel,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_file")
    val imageFile: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)