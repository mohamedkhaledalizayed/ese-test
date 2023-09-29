package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName

data class ArchiveUploadItemData(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("title")
    var name: String?,
    @field:SerializedName("description")
    var description: String?,
    @field:SerializedName("category_id")
    var categoryId: Int = 0,
    @field:SerializedName("doc1")
    var doc1: String?
)