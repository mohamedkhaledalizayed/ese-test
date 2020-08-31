package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["result"])
data class DecryptionData(
    @field:SerializedName("result")
    var result: String
) : Response()