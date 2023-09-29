package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName

data class ArchiveUploadCategoryData(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("title")
    var name: String?
)