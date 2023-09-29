package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ArchiveUploadRequest(
    @SerializedName("user_id")
    var userNumber: String = "",
    @SerializedName("title")
    var name: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("category_id")
    var catId: String = "",
    @SerializedName("docs_num")
    var docsNumber: Int = 0
) : Request()