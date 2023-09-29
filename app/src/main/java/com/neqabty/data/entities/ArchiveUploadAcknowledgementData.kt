package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName

data class ArchiveUploadAcknowledgementData(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("upload_type_id")
    var uploadTypeId: Int = 0,
    @field:SerializedName("user_id")
    var userId: Int = 0,
    @field:SerializedName("title")
    var name: String?,
    @field:SerializedName("description")
    var description: String?,
    @field:SerializedName("doc1")
    var doc1: String?
)